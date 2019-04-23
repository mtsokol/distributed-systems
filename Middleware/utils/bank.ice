
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
  struct Credentials { Pesel pesel; Password password; };
  
  struct CreditEstimate { Balance originCurrency; Balance foreignCurrency; };

  exception InvalidCredentialsException {
    string reason = "pesel of password invalid";
  };

  exception InvalidAccountTypeException {
    string reason = "credits are only for premium users";
  };

  interface Bank {
    AccountCreated createAccount(Name name, Surname surname, Pesel pesel, Balance income);
    Balance accountBalance(Credentials credentials) throws InvalidCredentialsException;
    CreditEstimate applyForCredit(Credentials credentials, Currency currency, Balance amount, Period period) throws InvalidAccountTypeException;
  };

};

