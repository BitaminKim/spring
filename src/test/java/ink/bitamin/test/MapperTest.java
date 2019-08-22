package ink.bitamin.test;

import ink.bitamin.dao.AccountMapper;
import ink.bitamin.dao.DepartmentMapper;
import ink.bitamin.entity.Account;
import ink.bitamin.entity.AccountExample;
import ink.bitamin.entity.vo.AccountVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class MapperTest {

    @Autowired
    DepartmentMapper departmentMapper;

    @Autowired
    AccountMapper accountMapper;

    @Test
    public void testCRUD(){

        AccountExample accountExample = new AccountExample();
        List<AccountVO> accounts = accountMapper.selectByExampleWithDepartment(accountExample);
        AccountVO account = accountMapper.selectByPrimaryKeyWithDepartment(Long.valueOf(1));
        Account account2 = (Account)accountMapper.selectByPrimaryKey(Long.valueOf(1));

        accounts.forEach(item-> System.out.println(item));
        System.out.println(account);
        System.out.println(account2);
    }
}
