package com.oneq.programmingpracticeplatform.typehandler;

import com.oneq.programmingpracticeplatform.model.enums.AuthEnum;
import com.oneq.programmingpracticeplatform.util.EnumUtil;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.mybatis.spring.annotation.MapperScan;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(AuthEnum.class)
public class AuthTypeHandler extends BaseTypeHandler<AuthEnum> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, AuthEnum parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getValue());
    }

    @Override
    public AuthEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int code = rs.getInt(columnName);
        return (code == 0 && rs.wasNull()) ? null : EnumUtil.getEnumByValue(AuthEnum.class, code);
    }

    @Override
    public AuthEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int code = rs.getInt(columnIndex);
        return (code == 0 && rs.wasNull()) ? null : EnumUtil.getEnumByValue(AuthEnum.class, code);
    }

    @Override
    public AuthEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int code = cs.getInt(columnIndex);
        return (code == 0 && cs.wasNull()) ? null : EnumUtil.getEnumByValue(AuthEnum.class, code);
    }
}
