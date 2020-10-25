package com.siwuxie095.functional.chapter8th.example6th.traditional;

/**
 * 员工申请贷款是个人申请的一种特殊情况
 *
 * @author Jiajing Li
 * @date 2020-10-25 20:03:02
 */
@SuppressWarnings("all")
public class EmployeeLoanApplication extends PersonalLoanApplication {

    @Override
    protected void checkIncomeHistory() {
        // They work for us!
    }

}
