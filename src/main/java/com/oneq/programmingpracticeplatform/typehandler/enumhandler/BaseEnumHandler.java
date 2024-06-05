package com.oneq.programmingpracticeplatform.typehandler.enumhandler;

import com.oneq.programmingpracticeplatform.model.enums.AuthEnum;
import com.oneq.programmingpracticeplatform.model.enums.base.BaseEnum;
import com.oneq.programmingpracticeplatform.util.EnumUtil;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes({AuthEnum.class})
public abstract class BaseEnumHandler<E extends BaseEnum> extends BaseTypeHandler<E> {

    private final Class<E> type;

    public BaseEnumHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getValue());
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int code = rs.getInt(columnName);
        return (code == 0 && rs.wasNull()) ? null : EnumUtil.getEnumByValue(type, code);
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int code = rs.getInt(columnIndex);
        return (code == 0 && rs.wasNull()) ? null : EnumUtil.getEnumByValue(type, code);
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int code = cs.getInt(columnIndex);
        return (code == 0 && cs.wasNull()) ? null : EnumUtil.getEnumByValue(type, code);
    }
}