#include <Wire.h> 
#include <LiquidCrystal_I2C.h>

//constante des inputs,TODO A REDEFINIR
const int inputButton[] = {A0,A1,A2};
const int inputSwitch[] = {A0,A1,A2};
//constante des variables d'états des entrées, TODO A REDEFINIR
int currentStateInputSwitch[] = {HIGH,HIGH,HIGH};//etat relaché par défaut
int currentStateInputButton[] = {HIGH,HIGH,HIGH};

int currentStateRead; 

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
  
  //initialisation des entrées et leur mode
  for (int i = 0; i < sizeof(inputsButton); i++) {
    pinMode(inputButton[i], INPUT_PULLUP);
  }
  for (int i = 0; i < sizeof(inputsSwitch); i++) {
    pinMode(inputsSwitch[i], INPUT_PULLUP);
  }
}

void loop() {
  
  //lecture et interpretation pour les interrupteurs
  for (int i = 0; i < sizeof(inputsSwitch); i++) {
    if (inputsSwitch[i] 
  }
  //TODO
}

void serialEvent() {
  //TODO
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
    lcd.print("System T° : ");
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
