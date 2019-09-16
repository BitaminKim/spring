package ink.bitamin.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import ink.bitamin.annotation.LogAnnotate;
import ink.bitamin.entity.vo.MessageVO;
import ink.bitamin.entity.vo.AccountVO;
import ink.bitamin.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class AccountController {

    @Autowired
    AccountService accountService;

    @LogAnnotate("getAccount_method")
    @ResponseBody
    @RequestMapping(value = "/account/users/pageNum/{pageNum}", method = RequestMethod.GET, produces = "application/json")
    public MessageVO getAccounts(
//            @RequestBody Map<String, String> map,
            @PathVariable Integer pageNum,
            @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){
//        System.out.println(map.get("password"));
        PageHelper.startPage(pageNum, pageSize);
        List<AccountVO> allWithDepartment = accountService.getAllWithDepartment();
        PageInfo pageInfo = new PageInfo(allWithDepartment,9);
        return MessageVO.success().add("pageInfo", pageInfo);
    }

}
