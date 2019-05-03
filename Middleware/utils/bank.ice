module BankSystem {

  enum Currency { PLN, GBP, USD, CHF, EUR };
  enum AccountType { STANDARD, PREMIUM };

  struct Password { string value; };
  struct Pesel { long value; };
  struct Balance { double value; };
  struct Name { string value; };
  struct Surname { string value; };
  struct Period { string value; };

  struct AccountCreated { Password password; AccountType accountType; };
  struct CreditEstimate { Balance originCurrency; Balance foreignCurrency; };

  exception InvalidCredentialsException {
    string reason = "pesel or password invalid";
  };

  exception InvalidAccountTypeException {
    string reason = "credits are only for premium users";
  };

  interface Account {
    AccountType getAccountType();
    Balance getAccountBalance();
    CreditEstimate applyForCredit(Currency currency, Balance amount, Period period) throws InvalidAccountTypeException;
  };

  interface AccountStandard extends Account {};

  interface AccountPremium extends Account {};

  interface AccountFactory {
    AccountCreated createAccount(Name name, Surname surname, Pesel pesel, Balance income);
    Account* obtainAccess(Pesel pesel) throws InvalidCredentialsException;
  };
  
};
