package com.siwuxie095.functional.chapter8th.example6th.traditional;

import com.siwuxie095.functional.chapter8th.example6th.ApplicationDenied;
import org.junit.Test;

/**
 * @author Jiajing Li
 * @date 2020-10-25 20:25:29
 */
@SuppressWarnings("all")
public class Client {

    @Test
    public void fitsTogether() throws ApplicationDenied {
        new CompanyLoanApplication().checkLoanApplication();
        new PersonalLoanApplication().checkLoanApplication();
        new EmployeeLoanApplication().checkLoanApplication();
    }

}
