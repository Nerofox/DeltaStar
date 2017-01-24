#include <Wire.h> 
#include <LiquidCrystal_I2C.h>

//constante des leds
const int led[] = {32,31,30,29,28,27,26,25,24,23,22,2,4,5};
const int nbLed = 14;
//donnée des clignotement pour les leds
int ledBlink[] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0};
//constante des inputs
const int inputButton[] = {35,36,37,38,39,40,41,42,43,44,45};
const int inputSwitch[] = {A0,34,A2,A3,A4,A5,A6,A7,A8,A9,A10,A11,A12,33,A14,A15,53,52,51,50,49,48,47,46};
const int nbSwitch = 24;
const int nbButton = 11;
//constante des variables d'états des entrées
//etat relaché par défaut LOW
int currentStateInputSwitch[] = {LOW,LOW,LOW,LOW,LOW,LOW,LOW,LOW,LOW,LOW,LOW,LOW,LOW,LOW,LOW,LOW,LOW,LOW,LOW,LOW,LOW,LOW,LOW,LOW};
int currentStateInputButton[] = {LOW,LOW,LOW,LOW,LOW,LOW,LOW,LOW,LOW,LOW,LOW};
int finalStateInputButton[] = {LOW,LOW,LOW,LOW,LOW,LOW,LOW,LOW,LOW,LOW,LOW};

int currentStateRead;
int currentPort;

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
  for (int i = 0; i < nbLed; i++) {
    pinMode(led[i], OUTPUT);
    digitalWrite(led[i], HIGH);
  }
  
  //initialisation des entrées et leur mode
  for (int i = 0; i < nbButton; i++) {
    pinMode(inputButton[i], INPUT_PULLUP);
  }
  for (int i = 0; i < nbSwitch; i++) {
    pinMode(inputSwitch[i], INPUT_PULLUP);
  }

  //etalonnage des etats des boutons actuelles
  for (int i = 0; i < nbButton; i++) {
    currentStateRead = digitalRead(inputButton[i]);
    currentStateInputButton[i] = currentStateRead;
    finalStateInputButton[i] = currentStateRead;
  }
  Serial.println();
}

void loop() {
  for (int i = 0; i < nbLed; i++) {
    if (ledBlink[i] == 1)
      digitalWrite(led[i], LOW);
  }
  delay(200);
  for (int i = 0; i < nbLed; i++) {
    if (ledBlink[i] == 1)
      digitalWrite(led[i], HIGH);
  }
  delay(200);
  
  //lecture et interpretation pour les interrupteurs
  for (int i = 0; i < nbSwitch; i++) {
    currentStateRead = digitalRead(inputSwitch[i]);
    if (currentStateRead != currentStateInputSwitch[i]) {
      String output = String(i);
      if (i < 10) {
        output = "0" + output;
      }
      Serial.print(output);
      currentStateInputSwitch[i] = currentStateRead;
    }
  }

  currentPort = nbSwitch;
  //lecture et interprétation des boutons
  for (int i = 0; i < nbButton; i++) {
    currentStateRead = digitalRead(inputButton[i]);
    if (currentStateRead != currentStateInputButton[i] && finalStateInputButton[i] != currentStateRead) {
      String output = String(currentPort);
      if (currentPort < 10) {
        output = "0" + output;
      }
      Serial.print(output);
    }
    currentStateInputButton[i] = currentStateRead;
    currentPort++;
  }
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
  for (i = 0; i < nbLed; i++) {
    if (inputSerial.substring(i, i+1) == "0") {
      digitalWrite(led[i], HIGH);
      ledBlink[i] = 0;
    } else if (inputSerial.substring(i, i+1) == "1") {
      digitalWrite(led[i], LOW);
      ledBlink[i] = 0;
    } else if (inputSerial.substring(i, i+1) == "2") {
      ledBlink[i] = 1;
    }
  }
  //gestion de l'écran
  setLcd(inputSerial.substring(i, i+1), inputSerial.substring(i+1, i+4), inputSerial.substring(i+4, i+7));
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
  if ( arg_options == "1" ) {
    lcd.print("No connection");
    lcd.setCursor(0,1);
    lcd.print("on APU");
  } 
  if ( arg_options == "2" ) {
    lcd.print("!!Warning!!");
    lcd.setCursor(0,1);
    lcd.print("O2/N2 low");
  }
  if (arg_options == "3") {
    lcd.print("!!Warning!!");
    lcd.setCursor(0,1);
    lcd.print("hoverheated");
  }
  if ( arg_options == "4") {
    lcd.print("O2/N2 : ");
    lcd.setCursor(0,1);
    lcd.print("System T : ");
    lcd.setCursor(9,0);
    lcd.print(arg_num1 + " %");
    lcd.setCursor(9,1);
    lcd.print(arg_num2 + " C");
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
