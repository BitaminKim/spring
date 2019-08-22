package ink.bitamin.entity.vo;

import ink.bitamin.entity.Account;

public class AccountVO extends Account {

    private DepartmentVO departmentVO;

    public DepartmentVO getDepartmentVO() {
        return departmentVO;
    }

    public void setDepartmentVO(DepartmentVO departmentVO) {
        this.departmentVO = departmentVO;
    }

}
