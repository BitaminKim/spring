package ink.bitamin.dao;

import ink.bitamin.entity.BasicDO;
import ink.bitamin.entity.BasicExampleDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BasicMapper {
    long countByExample(BasicExampleDO example);

    int deleteByExample(BasicExampleDO example);

    int deleteByPrimaryKey(Long id);

    int insert(BasicDO record);

    int insertSelective(BasicDO record);

    List<BasicDO> selectByExample(BasicExampleDO example);

    BasicDO selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") BasicDO record, @Param("example") BasicExampleDO example);

    int updateByExample(@Param("record") BasicDO record, @Param("example") BasicExampleDO example);

    int updateByPrimaryKeySelective(BasicDO record);

    int updateByPrimaryKey(BasicDO record);
}
