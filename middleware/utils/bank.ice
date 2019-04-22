
module BankSystem {

  enum Currency { PLN, GBP, USD, CHF, EUR };
  enum AccountType { STANDARD, PREMIUM };

  struct Password { string value; };
  struct Pesel { long value; };
  struct Balance { double value; };

  struct AccountCreated { Password password; AccountType accountType; };
  struct Credentials { Pesel pesel; Password password; };
  
  struct CreditEstimate { double originCurrency; double foreignCurrency; };

  interface Bank {
    AccountCreated createAccount(string name, string surname, Pesel pesel, long income);
    Balance accountBalance(Credentials credentials);
    CreditEstimate applyForCredit(Credentials credentials, Currency currency, long amount, string period);
  };

};

