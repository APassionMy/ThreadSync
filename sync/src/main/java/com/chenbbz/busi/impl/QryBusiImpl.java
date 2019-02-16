package com.chenbbz.busi.impl;

import com.chenbbz.busi.QryBusi;
import com.chenbbz.middle.model.Student;
import com.chenbbz.util.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class QryBusiImpl implements QryBusi {

    private static final Logger LOGGER = LoggerFactory.getLogger(QryBusiImpl.class);

    @Override
    public List queryList(int count) {
        SqlSession middleSession = SqlSessionUtil.getSqlSession("middle");
        List<Object> objects = null;
        try {
            objects = middleSession.selectList("com.chenbbz.middle.mapper.StudentMapper.selectList", count);
        } catch (Exception e) {
            LOGGER.error("查询发生异常=====》", e);
        } finally {
            middleSession.close();
        }
        return objects;
    }

    @Override
    public int modifyListStatus(List data, String status) {
        List<Student> students = data;

        students.forEach(student -> {
            student.setDataStatus(status);
            SqlSession middle = SqlSessionUtil.getSqlSession("middle");

            try {
                middle.update("com.chenbbz.middle.mapper.StudentMapper.updateByPrimaryKey", student);
                middle.commit();
            } catch (Exception e) {
                LOGGER.error("修改状态失败=======》",e);
            } finally {
                middle.close();
            }

        });

        return 0;
    }
}
