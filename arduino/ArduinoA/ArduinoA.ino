#include <Wire.h> 
#include <LiquidCrystal_I2C.h>

//constante des leds
const int led[] = {A0,A1,A2,2,3,4,5,6,7,8,9,10,11};
//donnée des clignotement pour les leds
int ledBlink[] = {0,0,0,0,0,0,0,0,0,0,0,0,0};

//donne entrant du port série
String inputSerial = "";
//initialisation de l'écran
LiquidCrystal_I2C lcd(0x27,16,2);

void setup() {
  //mise en place de l'écran
  lcd.begin();
  lcd.backlight();
  lcd.setCursor(0,0);

  //ecoute du port série
  Serial.begin(9600);

  //initialisation des leds et leur mode
  for (int i = 0; i < sizeof(led); i++) {
    pinMode(led[i], OUTPUT);
    digitalWrite(led[i], HIGH);
  }
}

void loop() {
  for (int i = 0; i < sizeof(ledBlink); i++) {
    if (ledBlink[i] == 1)
      digitalWrite(led[i], LOW);
  }
  delay(200);
  for (int i = 0; i < sizeof(ledBlink); i++) {
    if (ledBlink[i] == 1)
      digitalWrite(led[i], HIGH);
  }
  delay(200);
}

void serialEvent() {
  //récupération des données
  inputSerial = "";
  while (Serial.available() > 0)
  {
    inputSerial = inputSerial + ascii(Serial.read());
  }

  //lecture de la donnée entrante et allumage des LED
  int i;
  for (i = 0; i < sizeof(led); i++) {
    if (inputSerial.substring(i, i+1) == "0") {
      digitalWrite(led[i], HIGH);
    } else if (inputSerial.substring(i, i+1) == "1") {
      digitalWrite(led[i], LOW);
    } else if (inputSerial.substring(i, i+1) == "2") {
      ledBlink[i] = 1;
    } else {
      ledBlink[i] = 0;
    }
  }
  //gestion de l'écran
  setLcd(inputSerial.substring(i, i+1), inputSerial.substring(i, i+3), inputSerial.substring(i, i+5));
}

/**
 * fonction de changement d'état de l'écran LCD 
 * partie gestion de l'oxygen et de la température du liquide de refroidissement
 */
void setLcd(String arg_options, String arg_num1, String arg_num2) {
  //réinitialisation de l'écran
  lcd.setCursor(0,0);
  lcd.print("                ");
  lcd.setCursor(0,1);
  lcd.print("                ");  
  lcd.setCursor(0,0);
  
  //message selon option si option 0 aucun affichage
  if ( arg_options == 1 ) lcd.print("No connection"); 
  if ( arg_options == 2 ) {
    lcd.print("!!Warning!!");
    lcd.setCursor(0,1);
    lcd.print("Main low");
  }
  if ( arg_options == 3 ) {
    lcd.print("!!Warning!!");
    lcd.setCursor(0,1);
    lcd.print("RCS low");
  }
  if ( arg_options == 4 ) {
    lcd.print("Main : ");
    lcd.setCursor(0,1);
    lcd.print("RCS : ");
    lcd.setCursor(8,0);
    lcd.print(arg_num1 + " L");
    lcd.setCursor(10,1);
    lcd.print(arg_num2 + " L");
  }   
}

/**
 * fonction de traduction des données entrant en asciis
 */
String ascii(int arg_dec) {
  if (arg_dec == 48) return "0";
  if (arg_dec == 49) return "1";
  if (arg_dec == 50) return "2";
  if (arg_dec == 51) return "3";
  if (arg_dec == 52) return "4";
  if (arg_dec == 53) return "5";
  if (arg_dec == 54) return "6";
  if (arg_dec == 55) return "7";
  if (arg_dec == 56) return "8";
  if (arg_dec == 57) return "9";
}


