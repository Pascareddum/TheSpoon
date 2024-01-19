# The Spoon
![GitHub](https://img.shields.io/github/license/pascareddum/TheSpoon?color=red&style=flat-square)
<p align="center">
<img src="https://github.com/Pascareddum/TheSpoon/blob/master/Static/Image/logo_The_Spoon.png" width="200" height="300"/>
</p>

<p align = "center">
  🍕 Cloud-Based Reservation and Food Order System 🍕
  <br>
  A project for
  <br>
  Software Engineering 
  <br>
  courses of Computer Science at University of Salerno.
</p>

## Introduction 

"The Spoon" is a web-application based on Java Spring that provides a cloud-based reservation and food order system. It integrates Telegram and POS APIs, was created for the Software Engineering Exam project at the Univerisity of Salerno.

This WebApp allow users to reserve a table or order food in their favorite restaurants, thanks to the integration with Telegram API all the notifications regarding food order or reservations can be smoothly sent through one of the most popular messaging apps, furthermore, by using the POS APIs, users will be able to pay for their order conveniently from the TheSpoon totem or on their devices.

Build with Java, Javascript, MySQL and love :heart:

## Authors

* **Alessandro Pascarella** - *Developer* - [Pascareddum](https://github.com/Pascareddum)
* **Jacopo Gennaro Esposito** - *Developer* - [jacopoesposito](https://github.com/jacopoesposito)
* **Vincenzo Catone** - *Developer* - [v1n555](https://github.com/v1n55)

## Documentation

* Process documentation can be found in *DOC*  directory. (**In Italian :it:**)
* TheSpoon software documentation, including: TheSpoonAPI, JaCoCo coverage and JavaDoc can be found [here](https://jacopoesposito.github.io/thespoon). (**In English :uk:**)

## The Spoon Ecosystem 

The Spoon consists of three main components: TheSpoon (Rest Server API), TheSpoonFrontend (SolidJS Frontend) and TheSpoonBot (Telegram Bot Server)

* [TheSpoonBot](https://github.com/jacopoesposito/TheSpoonBot)
* [TheSpoonFrontend](https://github.com/pascareddum/TheSpoonFrontend)
* [TheSpoonApiRestServer](https://github.com/pascareddum/TheSpoon)

## Requirements

* JDK 21 
* Maven 
* Spring Boot
* A Telegram bot created on Telegram and its API key/token, installation guide [here](https://github.com/jacopoesposito/TheSpoonBot).
* A developer account on Stripe and its API key.
* MySQL, installed and configured.

## Installation

### Clone this repository

* First make a clone of the repository

```
git clone https://github.com/jacopoesposito/TheSpoon.git
```

### Run the database script 

```
mysql -u your-mysql-username -p < thespoon.sql
```

### Set Up Environment Variables

Note: This guide is for Unix-like system on Windows, you can use the set command instead of export.

1. Set the environment variable for the Telegram bot API key:

```
export TELEGRAMBOTAPI=your_telegram_bot_token
```

Replace your_telegram_bot_token with the actual token obtained from BotFather.

2. Set the environment variable for the Stripe API Key:

```
export STRIPEKEY=your-stripe-api-key
```

Replace your-stripe-api-key with the actual API Key obtained from [Stripe Developer Dashboard](https://dashboard.stripe.com/).

3. Set the environment variable for the Stripe Webhook Secret 

```
export WEBHOKSTRIPESECRET=your-stripe-webhook-secret
```

Replace your-stripe-webhook-secret with the actual Webhook secret obtained from [Stripe Developer Dashboard](https://dashboard.stripe.com/).

4. Set the environment variable for the Database Password

```
export DBPASS=your-mysql-password
```

Replace your-mysql-password with the actual Password of your database.

5. Set the environment variable for the JWT Secret.

```
export SECRETJWT=your-jwt-secret
```

Replace your-jwt-secret with a Base64 encoded 32 character string 

### Build the project 

1. Move to the project directory and use maven to build it

```
mvn clean install
```

### Run it

```
java -jar target/your-application.jar
```

### Post-installation 

1. Start TheSpoonBot, companion Telegram Bot to receive notification regarding food order and reservation, 
installation guide [here](https://github.com/jacopoesposito/TheSpoonBot)

2. Start TheSpoonFrontend, SolidJS frontend for TheSpoon, installation guide [here](https://github.com/pascareddum/TheSpoonFrontend)

3. **Have fun!**



## Buid with 

* [Java](https://jdk.java.net/21/) - The programming language used for the back-end development.
* [Maven](https://maven.apache.org) - The dependency management tool.
* [Spring Framework](https://spring.io) - The java framework used (MVC/Web).
* [HTML5](https://en.wikipedia.org/wiki/HTML5) - The markup language used for the front-end. 
* [Javascript](https://ecma-international.org/publications-and-standards/standards/ecma-262/) - The programming language used for the front-end development.
* [SolidJS](https://www.solidjs.com/) - The js framework used for the front-end.
* [TailwindCSS](https://tailwindcss.com/) - The CSS framework used for the front-end.
* [TelegramBotAPI](https://core.telegram.org/bots/api) - The Telegram Bot Api used to build [TheSpoonBot](https://github.com/jacopoesposito/TheSpoonBot).
* [StripeAPI](https://stripe.com) - The Stripe API used to enable payments.
* [MySQL](https://dev.mysql.com/doc/) - The RDBMS used for the persistent data.
* **Love** :heart:

## Contributors

<a href="https://github.com/pascareddum/TheSpoon/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=pascareddum/TheSpoon" />
</a>

## License
[GNU/AGPL 3.0](https://choosealicense.com/licenses/agpl-3.0/)
