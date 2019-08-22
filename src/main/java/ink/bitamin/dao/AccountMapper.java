package ink.bitamin.dao;

import ink.bitamin.entity.AccountExample;
import java.util.List;

import ink.bitamin.entity.vo.AccountVO;

public interface AccountMapper extends BasicMapper {

    List<AccountVO> selectByExampleWithDepartment(AccountExample example);

    AccountVO selectByPrimaryKeyWithDepartment(Long id);
}