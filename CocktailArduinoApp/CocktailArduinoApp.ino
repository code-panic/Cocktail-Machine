#include <SoftwareSerial.h>
#include <LiquidCrystal_I2C.h>

SoftwareSerial BlueToothSerial(2, 3);   //bluetooth module Tx:Digital 2 Rx:Digital 3
LiquidCrystal_I2C lcd(0x27, 16, 2);  // I2C LCD 객체 선언

int led = 6;

int coladamix = 7;
int grenadine = 8;
int lime = 9;
int pineapple = 10;
int lemon = 11;
int cider = 12;
int orange = 13;

void setup() {
  Serial.begin(9600);
  BlueToothSerial.begin(9600);

  pinMode(led,OUTPUT);
  
  pinMode(coladamix,OUTPUT);
  pinMode(cider,OUTPUT);
  pinMode(lime,OUTPUT);
  pinMode(pineapple,OUTPUT);
  pinMode(lemon,OUTPUT);
  pinMode(grenadine,OUTPUT);
  pinMode(orange,OUTPUT);
  
  lcd.begin();
  lcd.backlight();
  lcd.setCursor(0,0);
  lcd.print("OVERFLOW");
  
  Serial.println("-- 아두이노 세팅 완료 --");
}

void loop() { 
  if (BlueToothSerial.available()){
    char message = BlueToothSerial.read();
    Serial.write(message);
    
    digitalWrite(led,HIGH);
    lcd.setCursor(0,1);
    
    switch(message){
      case '1'://모히토
        lcd.print("mohito");
        
        digitalWrite(lime,HIGH);
        digitalWrite(lemon,HIGH);
        digitalWrite(cider,HIGH);
        digitalWrite(pineapple,HIGH);
        delay(500);
        digitalWrite(lemon,LOW);
        digitalWrite(lime,LOW);
        delay(3000);
        digitalWrite(cider,LOW);      
        digitalWrite(pineapple,LOW);

        Serial.write("모히또가 완성되었습니다!");
        BlueToothSerial.print("complete"); 
        
        break;
      case '2'://선라이즈
        lcd.print("sunrise");    
        
        digitalWrite(pineapple,HIGH);
        delay(6000);
        digitalWrite(pineapple,LOW);
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
        delay(1500);
        digitalWrite(grenadine,LOW);
        digitalWrite(lemon,LOW);
        delay(1500);
        digitalWrite(orange,LOW);
        delay(3000);
        digitalWrite(pineapple,LOW);
        break;
        
      case '4'://신데렐라
        lcd.print("cinderella");
        
        digitalWrite(pineapple,HIGH);
        digitalWrite(orange,HIGH);
        digitalWrite(lemon,HIGH);
        delay(500);
        digitalWrite(lemon,LOW);
        delay(3000);
        digitalWrite(pineapple,LOW);
        digitalWrite(orange,LOW);
        break;   
        
      case '5'://피나콜라다
        lcd.print("pinacolada");
        
        digitalWrite(pineapple,HIGH);
        digitalWrite(coladamix,HIGH);
        delay(1500);
        digitalWrite(coladamix,LOW);
        delay(3000);
        digitalWrite(pineapple,LOW);
        break;   
    }
    digitalWrite(led,LOW);

    lcd.clear();
    lcd.setCursor(0,0);
    lcd.print("OVERFLOW");

    BlueToothSerial.print("complete");
  }
}
