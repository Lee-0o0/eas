package com.lee.eas.mapper;

import com.lee.eas.domain.po.StudentPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface StudentMapper {
    /**
     * 根据ID获取学生信息
     * @param id 学生ID
     * @return
     */
    StudentPO getStudentById(int id);

    /**
     * 根据学号获取学生信息
     * @param number 学号
     * @return
     */
    StudentPO getStudentByStudentNumber(String number);

    /**
     * 统计学生数量
     * @return
     */
    int countStudent();

    /**
     * 根据分页信息查询学生信息
     * @param offset 偏移量
     * @param pageSize 返回数量
     * @return
     */
    List<StudentPO> queryAllStudents(int offset, int pageSize);

    /**
     * 查询所有的学生信息
     * @return
     */
    List<StudentPO> queryAll();

    /**
     * 新增学生
     * @param studentPO
     */
    int insertStudent(StudentPO studentPO);

    /**
     * 修改学生信息
     * @param studentPO
     * @return
     */
    int updateStudent(StudentPO studentPO);

    /**
     * 根据ID删除学生
     * @param id
     * @return
     */
    int deleteStudent(int id);
}
