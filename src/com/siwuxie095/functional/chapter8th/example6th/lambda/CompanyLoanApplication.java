package com.siwuxie095.functional.chapter8th.example6th.lambda;

/**
 * @author Jiajing Li
 * @date 2020-10-25 20:06:25
 */
@SuppressWarnings("all")
public class CompanyLoanApplication extends LoanApplication {

    public CompanyLoanApplication(Company company) {
        super(company::checkIdentity,
                company::checkHistoricalDebt,
                company::checkProfitAndLoss);
    }

}
