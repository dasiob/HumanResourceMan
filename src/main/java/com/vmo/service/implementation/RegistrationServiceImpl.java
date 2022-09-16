package com.vmo.service.implementation;

import com.vmo.common.config.MapperUtil;
import com.vmo.common.enums.DefaultDepartmentsNames;
import com.vmo.common.enums.RoleNames;
import com.vmo.models.entities.Department;
import com.vmo.models.entities.EmailConfirmToken;
import com.vmo.models.entities.Role;
import com.vmo.models.entities.User;
import com.vmo.models.request.UserDto;
import com.vmo.models.response.Message;
import com.vmo.repository.DepartmentRepository;
import com.vmo.repository.EmailConfirmTokenRepository;
import com.vmo.repository.RoleRepository;
import com.vmo.repository.UserRepository;
import com.vmo.service.RegistrationService;
import com.vmo.service.SendEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Transactional
@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private EmailConfirmTokenRepository emailConfirmTokenRepository;
    @Autowired
    private SendEmailService sendEmailService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public void saveConfirmationToken(User user, String token) {
        EmailConfirmToken emailConfirmToken = new EmailConfirmToken(token, LocalDateTime.now().plusMinutes(10), user);
        emailConfirmTokenRepository.save(emailConfirmToken);
    }

    @Override
    public Message register(UserDto userDto) {

        User user = MapperUtil.map(userDto, User.class);
        List<Department> departmentList = new ArrayList<>();
        departmentList.add(departmentRepository.findBydepartmentName(DefaultDepartmentsNames.NOT_SET.toString()));
        user.setDepartments(departmentList);
        List<Role> roleList = new ArrayList<>();
        roleList.add(roleRepository.findByroleName(RoleNames.ROLE_USER));
        user.setRoles(roleList);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setDeleted(true);
        userRepository.save(user);

        String mailConfirmToken = UUID.randomUUID().toString();
        saveConfirmationToken(user, mailConfirmToken);
        String link = "http://localhost:8080/api/v1/auth/signup/confirm?token=" + mailConfirmToken;
        sendEmailService.sendEmail(userDto.getEmail(), buildEmail(userDto.getUserName(), link));
        return new Message("Token sent to mail succesfully");
    }

    @Override
    public String confirmToken(String token) {
        EmailConfirmToken emailConfirmToken =  emailConfirmTokenRepository.findBytoken(token).orElse(null);
        if (emailConfirmToken.getConfirmedAt() != null) {
            throw new IllegalStateException("This mail is already confirmed");
        }

        LocalDateTime expiresAt = emailConfirmToken.getExpiresAt();

        if (expiresAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Your mail confirmation is expired");
        }

        emailConfirmTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
        userRepository.enableUser(emailConfirmToken.getUser().getEmail());
        return "Your email is now verified";
    }

    @Override
    public String buildEmail(String name, String link) {
        return "<div style=\"display: none; font-size: 1px; line-height: 1px; max-height: 0; max-width: 0; opacity: 0; overflow: hidden; mso-hide: all; font-family: sans-serif;\">\n" +
                "    &nbsp;\n" +
                "</div>\n" +
                "<table style=\"background: #F7F8FA; border: 0; border-radius: 0; width: 100%;\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "<tbody>\n" +
                "<tr>\n" +
                "    <td class=\"tw-body\" style=\"padding: 15px 15px 0;\" align=\"center\">\n" +
                "        <table style=\"background: #F7F8FA; border: 0; border-radius: 0;\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "            <tbody>\n" +
                "            <tr>\n" +
                "                <td class=\"\" style=\"width: 600px;\" align=\"center\">\n" +
                "                    <p style=\"padding: 5px 5px 5px; font-size: 13px; margin: 0 0 0px; color: #316fea;\" align=\"right\">\n" +
                "                    </p>\n" +
                "                    <table style=\"background: #ffffff; border: 0px; border-radius: 4px; width: 99.6672%; overflow: hidden;\"\n" +
                "                           cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                        <tbody>\n" +
                "                        <tr>\n" +
                "                            <td class=\"\" style=\"padding: 0px; width: 100%;\" align=\"center\">\n" +
                "                                <table style=\"background: #336f85; border: 0px; border-radius: 0px; width: 599px; height: 53px; margin-left: auto; margin-right: auto;\"\n" +
                "                                       cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                                    <tbody>\n" +
                "                                    <tr>\n" +
                "\n" +
                "                                        <td class=\"tw-card-header\"\n" +
                "                                            style=\"padding: 5px 5px px; width: 366px; color: #ffff; text-decoration: none; font-family: sans-serif;\"\n" +
                "                                            align=\"center\"><span\n" +
                "                                                style=\"font-weight: 600;\">Email Confirmation Required</span></td>\n" +
                "\n" +
                "                                    </tr>\n" +
                "                                    </tbody>\n" +
                "                                </table>\n" +
                "                                <p><br/><br/></p>\n" +
                "                                <table dir=\"ltr\" style=\"border: 0; width: 100%;\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                                    <tbody>\n" +
                "                                    <tr>\n" +
                "                                        <td class=\"tw-card-body\"\n" +
                "                                            style=\"padding: 20px 35px; text-align: left; color: #6f6f6f; font-family: sans-serif; border-top: 0;\">\n" +
                "                                            <h1 class=\"tw-h1\"\n" +
                "                                                style=\"font-size: 24px; font-weight: bold; mso-line-height-rule: exactly; line-height: 32px; margin: 0 0 20px; color: #474747;\">\n" +
                "                                                Hello " + name + ",</h1>\n" +
                "                                            <p class=\"\"\n" +
                "                                               style=\"margin: 20px 0; font-size: 16px; mso-line-height-rule: exactly; line-height: 24px;\">\n" +
                "                                            <span style=\"font-weight: 400;\">Thank you for joining <strong>Customer Engagement App</strong>, an efficient and smart solution to manage your health!</span><br/><br/><span\n" +
                "                                                style=\"font-weight: 400;\">To complete the registration process, please confirm your email address to activate your account</span>\n" +
                "                                            <table style=\"border: 0; width: 100%;\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                                                <tbody>\n" +
                "                                                <tr>\n" +
                "                                                    <td>\n" +
                "                                                        <table class=\"button mobile-w-full\"\n" +
                "                                                               style=\"border: 0px; border-radius: 7px; margin: 0px auto; width: 525px; background-color: #008bcb; height: 50px;\"\n" +
                "                                                               cellspacing=\"0\" cellpadding=\"0\" align=\"center\">\n" +
                "                                                            <tbody>\n" +
                "                                                            <tr>\n" +
                "                                                                <td class=\"button__td \"\n" +
                "                                                                    style=\"border-radius: 7px; text-align: center; width: 523px;\"><!-- [if mso]>\n" +
                "                                                                    <a href=\"\" class=\"button__a\" target=\"_blank\"\n" +
                "                                                                       style=\"border-radius: 4px; color: #FFFFFF; display: block; font-family: sans-serif; font-size: 18px; font-weight: bold; mso-height-rule: exactly; line-height: 1.1; padding: 14px 18px; text-decoration: none; text-transform: none; border: 1px solid #316FEA;\"> </a>\n" +
                "                                                                    <![endif]--> <!-- [if !mso]><!--> <a\n" +
                "                                                                            class=\"button__a\"\n" +
                "                                                                            style=\"border-radius: 4px; color: #ffffff; display: block; font-family: sans-serif; font-size: 18px; font-weight: bold; mso-height-rule: exactly; line-height: 1.1; padding: 14px 18px; text-decoration: none; text-transform: none; border: 0;\"\n" +
                "                                                                            href=\"" + link + "\"\n" +
                "                                                                            target=\"_blank\"\n" +
                "                                                                            rel=\"noopener\">Confirm\n" +
                "                                                                        email</a> <!--![endif]--></td>\n" +
                "                                                            </tr>\n" +
                "                                                            </tbody>\n" +
                "                                                        </table>\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                                </tbody>\n" +
                "                                            </table>\n" +
                "                                            <div class=\"\"\n" +
                "                                                 style=\"border-top: 0; font-size: 1px; mso-line-height-rule: exactly; line-height: 1px; max-height: 0; margin: 20px 0; overflow: hidden;\">\n" +
                "                                                \u200B\n" +
                "                                            </div>\n" +
                "                                            <p class=\"\"\n" +
                "                                               style=\"margin: 20px 0; font-size: 16px; mso-line-height-rule: exactly; line-height: 24px;\">\n" +
                "                                                Contact our support team if you have any questions or concerns.&nbsp;<a\n" +
                "                                                    style=\"color: #316fea; text-decoration: none;\"\n" +
                "                                                    href=\"javascript:void(0);\" target=\"_blank\" rel=\"noopener\">Ask us any\n" +
                "                                                question</a></p>\n" +
                "                                            <p class=\"tw-signoff\"\n" +
                "                                               style=\"margin: 45px 0 5px; font-size: 16px; mso-line-height-rule: exactly; line-height: 24px;\">\n" +
                "                                                Our best, <br/> The Customer Engagement App team</p>\n" +
                "                                        </td>\n" +
                "                                    </tr>\n" +
                "                                    </tbody>\n" +
                "                                </table>\n" +
                "                            </td>\n" +
                "                        </tr>\n" +
                "                        </tbody>\n" +
                "                    </table>";
    }
}
