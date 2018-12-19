#include <LiquidCrystal_I2C.h>
#include <SoftwareSerial.h>
#include <Wire.h>

SoftwareSerial BTSerial(2, 3);   //bluetooth module Tx:Digital 2 Rx:Digital 3
LiquidCrystal_I2C lcd(0x27, 16, 2);

int coladamix = 6;
int cider = 7;
int lime = 8;
int pineapple = 9;
int lemon = 10;
int grenadine = 11;
int orange = 12;

void setup() {
  Serial.begin(9600);
  BTSerial.begin(9600);

  lcd.begin();
  lcd.print("OVERFLOW");
  
  pinMode(coladamix,OUTPUT);
  pinMode(cider,OUTPUT);
  pinMode(lime,OUTPUT);
  pinMode(pineapple,OUTPUT);
  pinMode(lemon,OUTPUT);
  pinMode(grenadine,OUTPUT);
  pinMode(orange,OUTPUT);
  
  Serial.println("ATcommand");  //ATcommand Start
}

void loop() {
  if (Serial.available()){
    BTSerial.write(Serial.read());
  }
  
  if (BTSerial.available()){
    char msg = BTSerial.read();
    Serial.write(msg);
    
    lcd.clear();
    lcd.backlight();
    lcd.print("Extracting ");
    
    switch(msg){
      case '1'://모히토
        lcd.print("mohito");
        
        digitalWrite(lime,HIGH);
        digitalWrite(orange,HIGH);
        digitalWrite(cider,HIGH);
        delay(1000);
        digitalWrite(lime,LOW);
        digitalWrite(orange,LOW);
        delay(1000);
        digitalWrite(cider,LOW);      
        break;
        
      case '2'://선라이즈
        lcd.print("sunrise");
      
        digitalWrite(orange,HIGH);
        delay(3000);
        digitalWrite(orange,LOW);
        digitalWrite(grenadine,HIGH);
        delay(1000);
        digitalWrite(grenadine,LOW);
        break;
           
      case '3'://선샤인
        lcd.print("sunshine");
        
        digitalWrite(pineapple,HIGH);
        digitalWrite(orange,HIGH);
        digitalWrite(lemon,HIGH);
        digitalWrite(grenadine,HIGH);
        delay(500);
        digitalWrite(grenadine,LOW);
        digitalWrite(lemon,LOW);
        delay(500);
        digitalWrite(orange,LOW);
        delay(1000);
        digitalWrite(pineapple,LOW);
        break;
        
      case '4'://신데렐라
        lcd.print("cinderella");
 
        digitalWrite(pineapple,HIGH);
        digitalWrite(orange,HIGH);
        digitalWrite(lemon,HIGH);
        delay(1330);
        digitalWrite(pineapple,LOW);
        digitalWrite(orange,LOW);
        digitalWrite(lemon,LOW);
        break;   
        
      case '5'://피나콜라다
        lcd.print("pinacolada");
        
        digitalWrite(pineapple,HIGH);
        digitalWrite(coladamix,HIGH);
        delay(1000);
        digitalWrite(coladamix,LOW);
        delay(2000);
        digitalWrite(pineapple,LOW);
        break;   
   }
   lcd.clear();
   lcd.print("OVERFLOW");
   lcd.noBacklight();
  }
}

void finishExtract(){
    lcd.clear();
    lcd.print("OVERFLOW"); 
    lcd.noBacklight();
}
