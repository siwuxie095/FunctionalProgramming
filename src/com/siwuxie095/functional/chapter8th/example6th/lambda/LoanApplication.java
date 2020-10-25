package com.siwuxie095.functional.chapter8th.example6th.lambda;

import com.siwuxie095.functional.chapter8th.example6th.ApplicationDenied;

/**
 * @author Jiajing Li
 * @date 2020-10-25 20:05:28
 */
@SuppressWarnings("all")
public class LoanApplication {

    private final Criteria identity;
    private final Criteria creditHistory;
    private final Criteria incomeHistory;

    public LoanApplication(Criteria identity,
                           Criteria creditHistory,
                           Criteria incomeHistory) {
        this.identity = identity;
        this.creditHistory = creditHistory;
        this.incomeHistory = incomeHistory;
    }

    public void checkLoanApplication() throws ApplicationDenied {
        identity.check();
        creditHistory.check();
        incomeHistory.check();
        reportFindings();
    }

    private void reportFindings() {}

}
