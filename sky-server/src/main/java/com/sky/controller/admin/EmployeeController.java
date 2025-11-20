package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@Api(tags = "员工相关接口")
@RestController
@RequestMapping("/admin/employee")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }
    @PostMapping()
    public Result addEmp(@RequestBody EmployeeDTO employeeDTO) {
        log.info("新增员工：{}", employeeDTO);
        employeeService.addEmp(employeeDTO);
        return Result.success();
    }
    @GetMapping("/page")
    public Result page(EmployeePageQueryDTO employeePageQueryDTO) {
        log.info("分页查询：{}", employeePageQueryDTO);
        PageResult pages =employeeService.page(employeePageQueryDTO);
        return Result.success(pages);
    }
    @PostMapping("/status/{status}")
    public Result enable(@PathVariable Integer status,Long id) {
        log.info("员工状态修改：status={},id={}",status,id);
        employeeService.enable(status,id);
        return Result.success();
    }
    @PutMapping()
    public Result updateEmp(@RequestBody EmployeeDTO employeeDTO) {
        log.info("员工信息修改,id={},id_number=#{},name=#{},phone=#{},sex=#{},username=#{}",employeeDTO.getId(),employeeDTO.getIdNumber()
        ,employeeDTO.getName(),employeeDTO.getPhone(),employeeDTO.getUsername());
        employeeService.updateEmp(employeeDTO);
        return Result.success();
    }
    @GetMapping("/{id}")
    public Result getEmpById(@PathVariable Long id) {
        log.info("根据id查询：{}",id);
        Employee employee = employeeService.getbyId(id);
        return Result.success(employee);
    }

}
