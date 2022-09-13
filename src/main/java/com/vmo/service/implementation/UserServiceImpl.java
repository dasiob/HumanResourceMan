package com.vmo.service.implementation;

import com.vmo.common.config.MapperUtil;
import com.vmo.common.enums.DefaultDepartmentsNames;
import com.vmo.common.enums.RoleNames;
import com.vmo.models.entities.Department;
import com.vmo.models.entities.Role;
import com.vmo.models.entities.User;
import com.vmo.models.request.DepartmentDto;
import com.vmo.models.request.RoleDto;
import com.vmo.models.request.UserDto;
import com.vmo.models.response.Message;
import com.vmo.models.response.UserPagingResponse;
import com.vmo.models.response.UserResponse;
import com.vmo.repository.DepartmentRepository;
import com.vmo.repository.RoleRepository;
import com.vmo.repository.UserRepository;
import com.vmo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Value("${project.image}")
    private String path;

    @Override
    @Transactional
    public UserPagingResponse getAllUsers(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        List<UserResponse> content = new ArrayList<>();
        Page<User> users = userRepository.findAll(pageable);
        List<User> userList = users.getContent();
        content.addAll(userList.stream().map(s -> MapperUtil.map(s, UserResponse.class)).collect(Collectors.toList()));
        for (int i = 0; i < userList.size(); i++) {
            List<RoleDto> roleDtoList = MapperUtil.mapList(userList.get(i).getRoles(), RoleDto.class);
            content.get(i).setRoleDtos(roleDtoList);
            List<DepartmentDto> departmentDtoList = MapperUtil.mapList(userList.get(i).getDepartments(), DepartmentDto.class);
            content.get(i).setDepartmentDtos(departmentDtoList);
        }

        UserPagingResponse userPagingResponse = new UserPagingResponse();
        userPagingResponse.setContent(content);
        userPagingResponse.setPageNo(users.getNumber());
        userPagingResponse.setPageSize(users.getSize());
        userPagingResponse.setTotalElements(users.getTotalElements());
        userPagingResponse.setTotalPages(users.getTotalPages());
        userPagingResponse.setLast(users.isLast());

        return userPagingResponse;
    }

    @Override
    @Transactional
    public UserResponse createUser(UserDto userDto, MultipartFile file) {
        UserResponse userResponse = new UserResponse();
        User user = MapperUtil.map(userDto, User.class);
        try {
            String fileName = file.getOriginalFilename();
            String randomID = UUID.randomUUID().toString();
            String fileName1 = randomID.concat(fileName.substring(fileName.lastIndexOf(".")));
            String filePath = path + File.separator + fileName1;

            File f = new File(path);
            if (!f.exists()) f.mkdir();

            Files.copy(file.getInputStream(), Path.of(filePath));

            Department department = departmentRepository.findBydepartmentName(DefaultDepartmentsNames.NOT_SET.toString());
            List<Department> departmentList = new ArrayList<>();
            departmentList.add(department);
            user.setDepartments(departmentList );

            user.setAvatarPath(filePath);
            user.setAvatarUrl(fileName1);
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            List<Role> roleList = new ArrayList<>();
            roleList.add(roleRepository.findByroleName(RoleNames.ROLE_USER));
            user.setRoles(roleList);
            userRepository.save(user);

            userResponse = MapperUtil.map(user, UserResponse.class);
            userResponse.setDepartmentDtos(MapperUtil.mapList(departmentList, DepartmentDto.class));
            userResponse.setRoleDtos(MapperUtil.mapList(roleList, RoleDto.class));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return userResponse;
    }

    @Override
    @Transactional
    public UserResponse updateUser(int userId, UserDto userDto, MultipartFile file) {
        UserResponse userResponse = new UserResponse();
        User existedUser = userRepository.findById(userId).get();
        try {
            String fileName = file.getOriginalFilename();
            String randomID = UUID.randomUUID().toString();
            String fileName1 = randomID.concat(fileName.substring(fileName.lastIndexOf(".")));
            String filePath = path + File.separator + fileName1;

            File f = new File(path);
            if (!f.exists()) f.mkdir();

            Files.copy(file.getInputStream(), Path.of(filePath));

            existedUser.setFirstName(userDto.getFirstName());
            existedUser.setLastName(userDto.getLastName());
            existedUser.setUserName(userDto.getUserName());
            existedUser.setPassword(userDto.getPassword());
            existedUser.setEmail(userDto.getEmail());
            existedUser.setPhone(userDto.getPhone());
            if (!(userDto.getDepartmentDtos() == null)) {
                existedUser.setDepartments(MapperUtil.mapList(userDto.getDepartmentDtos(), Department.class));
            }
            userRepository.save(existedUser);
            userResponse = MapperUtil.map(existedUser, UserResponse.class);
            userResponse.setDepartmentDtos(MapperUtil.mapList(existedUser.getDepartments(), DepartmentDto.class));
            userResponse.setRoleDtos(MapperUtil.mapList(existedUser.getRoles(), RoleDto.class));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return userResponse;
    }

    @Override
    @Transactional
    public Message deleteUser(int userId) {
        User user = userRepository.findById(userId).get();
        user.setDeleted(true);
        userRepository.save(user);
        return new Message("User is deleted");
    }

    @Override
    public UserResponse getUserById(int userId) {
        User user = userRepository.findById(userId).orElse(null);
        return MapperUtil.map(user, UserResponse.class);
    }

    @Override
    public User getUserByName(String userName) {
        return userRepository.findByuserName(userName);
    }

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String randomID = UUID.randomUUID().toString();
        String fileName1 = randomID.concat(fileName.substring(fileName.lastIndexOf(".")));
        String filePath = path + File.separator + fileName1;

        File f = new File(path);
        if (!f.exists()) f.mkdir();

        Files.copy(file.getInputStream(), Path.of(filePath));
        return fileName;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath = path + File.separator + fileName;
        InputStream is = new FileInputStream(fullPath);
        return is;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByuserName(username);
        if (user == null) throw new UsernameNotFoundException("User not found");
        else log.info("User found in the database: {}", username);
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName().toString()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), authorities);
    }
}
