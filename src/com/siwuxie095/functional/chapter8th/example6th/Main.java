package com.siwuxie095.functional.chapter8th.example6th;

/**
 * @author Jiajing Li
 * @date 2020-10-25 19:52:28
 */
@SuppressWarnings("all")
public class Main {

    /**
     * 模板方法模式
     *
     * 开发软件时一个常见的情况是有一个通用的算法，只是步骤上略有不同。希望这里不同的实现能够遵守通用模式，保证它们
     * 使用了同一个算法，也是为了让代码更加易读。一旦从整体上理解了算法，就能更容易理解其各种实现。
     *
     * 模板方法模式是为这些情况设计的：整体算法的设计是一个抽象类，它有一系列抽象方法，代表算法中可被定制的步骤，同
     * 时这个类中包含了一些通用代码。算法的每一个变种由具体的类实现，它们重写了抽象方法，提供了相应的实现。
     *
     * 可以假想一个情境来搞明白这是怎么回事。假设有一家银行，需要对公司、个人和职员放贷。放贷程序大体一致：验明身份、
     * 信用记录和收入记录。这些信息来源不一，衡量标准也不一样。比如个人可以查看一个家庭的账单来核对个人身份；而公司
     * 都在官方机构注册过，比如美国的 SEC、英国的 Companies House，同样进行验证。
     *
     * 先使用一个抽象类 LoanApplication 来控制算法结构，该类包含一些贷款调查结果报告的通用代码。根据不同的申请人，
     * 有不同的类：CompanyLoanApplication、PersonalLoanApplication 和 EmployeeLoanApplication。
     *
     * LoanApplication 类实现如下：
     *
     * public abstract class LoanApplication {
     *
     *     public void checkLoanApplication() throws ApplicationDenied {
     *         checkIdentity();
     *         checkCreditHistory();
     *         checkIncomeHistory();
     *         reportFindings();
     *     }
     *
     *     protected abstract void checkIdentity() throws ApplicationDenied;
     *
     *     protected abstract void checkIncomeHistory() throws ApplicationDenied;
     *
     *     protected abstract void checkCreditHistory() throws ApplicationDenied;
     *
     *     private void reportFindings() {}
     *
     * }
     *
     * CompanyLoanApplication 的 checkIdentity 方法在 Companies House 等注册公司数据库中查找相关信息。
     * checkIncomeHistory 方法评估公司的现有利润、损益表和资产负债表。checkCreditHistory 方法则查看现有
     * 的坏账和未偿债务（具体参见 CompanyLoanApplication 类）。
     *
     * PersonalLoanApplication 的 checkIdentity 方法通过分析客户提供的纸本结算单，确认客户地址是否真实有
     * 效。checkIncomeHistory 方法通过检查工资条判断客户是否仍被雇佣。checkCreditHistory 方法则会将工作
     * 交给外部的信用卡支付提供商（具体参见 PersonalLoanApplication 类）。
     *
     * EmployeeLoanApplication 就是没有查阅员工历史功能的 PersonalLoanApplication。为了方便起见，银行在
     * 雇佣员工时会查阅所有员工的收入记录（具体参见 EmployeeLoanApplication 类）。
     *
     * 使用 Lambda 表达式和方法引用，就能换个角度思考模板方法模式，实现方式也跟以前不一样。模板方法模式真正要做的是
     * 将一组方法调用按一定顺序组织起来。如果用函数式接口表示函数，用 Lambda 表达式或者方法引用实现这些接口，相比使
     * 用继承构建算法，就会得到极大的灵活性。
     *
     * 新的 LoanApplication 类实现如下：
     *
     * public class LoanApplication {
     *
     *     private final Criteria identity;
     *     private final Criteria creditHistory;
     *     private final Criteria incomeHistory;
     *
     *     public LoanApplication(Criteria identity,
     *                            Criteria creditHistory,
     *                            Criteria incomeHistory) {
     *         this.identity = identity;
     *         this.creditHistory = creditHistory;
     *         this.incomeHistory = incomeHistory;
     *     }
     *
     *     public void checkLoanApplication() throws ApplicationDenied {
     *         identity.check();
     *         creditHistory.check();
     *         incomeHistory.check();
     *         reportFindings();
     *     }
     *
     *     private void reportFindings() {}
     *
     * }
     *
     * 这里没有使用一系列的抽象方法，而是多出一些属性：identity、 creditHistory 和 incomeHistory。每一个属性都
     * 实现了函数式接口 Criteria，该接口检查一项标准，如果不达标就抛出一个问题域里的异常。也可以选择从 check 方法
     * 返回一个类来表示成功或失败，但是沿用异常更加符合先前的实现。
     *
     * Criteria 接口定义如下：
     *
     * public interface Criteria {
     *
     *     public void check() throws ApplicationDenied;
     *
     * }
     *
     * 采用这种方式，而不是基于继承的模式的好处是不需要在 LoanApplication 及其子类中实现算法，分配功能时有了更大的
     * 灵活性。
     *
     * 比如，想让 Company 类负责所有的检查，那么 Company 类就会多出一系列方法。
     *
     * Company 类实现如下：
     *
     * public class Company {
     *
     *     public void checkIdentity() throws ApplicationDenied {
     *
     *     }
     *
     *     public void checkProfitAndLoss() throws ApplicationDenied {
     *
     *     }
     *
     *     public void checkHistoricalDebt() throws ApplicationDenied {
     *
     *     }
     *
     * }
     *
     * 现在只需为 CompanyLoanApplication 类传入对应的方法引用，如下：
     *
     * public class CompanyLoanApplication extends LoanApplication {
     *
     *     public CompanyLoanApplication(Company company) {
     *         super(company::checkIdentity,
     *                 company::checkHistoricalDebt,
     *                 company::checkProfitAndLoss);
     *     }
     *
     * }
     *
     * 将行为分配给 Company 类的原因是各个国家之间确认公司信息的方式不同。在英国，Companies House 规范了注册公司
     * 信息的地址，但在美国，各个州的政策是不一样的。
     *
     * 使用函数式接口实现检查方法并没有排除继承的方式。可以显式地在这些类中使用 Lambda 表达式或者方法引用。
     *
     * 也不需要强制 EmployeeLoanApplication 继承 PersonalLoanApplication 来达到复用，可以对同一个方法传递引用。
     * 它们之间是否天然存在继承关系取决于员工的借贷是否是普通人借贷这种特殊情况，或者是另外一种不同类型的借贷。因此，
     * 使用这种方式能更加紧密地为问题建模。
     */
    public static void main(String[] args) {

    }

}
