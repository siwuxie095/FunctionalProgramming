package com.siwuxie095.functional.chapter8th.example6th.lambda;

import com.siwuxie095.functional.chapter8th.example6th.ApplicationDenied;
import com.siwuxie095.functional.chapter8th.example6th.traditional.EmployeeLoanApplication;
import com.siwuxie095.functional.chapter8th.example6th.traditional.PersonalLoanApplication;
import org.junit.Test;

/**
 * @author Jiajing Li
 * @date 2020-10-25 20:26:17
 */
@SuppressWarnings("all")
public class Client {

    @Test
    public void fitsTogether() throws ApplicationDenied {
        CompanyLoanApplication companyLoanApplication = new CompanyLoanApplication(new Company());
        companyLoanApplication.checkLoanApplication();
    }


}
