package com.siwuxie095.functional.chapter8th.example6th.traditional;

import com.siwuxie095.functional.chapter8th.example6th.ApplicationDenied;

/**
 * 使用模板方法模式描述申请贷款过程
 *
 * @author Jiajing Li
 * @date 2020-10-25 20:00:11
 */
@SuppressWarnings("all")
public abstract class LoanApplication {

    public void checkLoanApplication() throws ApplicationDenied {
        checkIdentity();
        checkCreditHistory();
        checkIncomeHistory();
        reportFindings();
    }

    protected abstract void checkIdentity() throws ApplicationDenied;

    protected abstract void checkIncomeHistory() throws ApplicationDenied;

    protected abstract void checkCreditHistory() throws ApplicationDenied;

    private void reportFindings() {}

}

