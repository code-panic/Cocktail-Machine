#include <SoftwareSerial.h>
#include <LiquidCrystal_I2C.h>

/* 관련 핀 상수화하기 */
#define TX 2
#define RX 3
#define LED 5

SoftwareSerial blueToothSerial(TX, RX);   // 블루투스 객체 선언
LiquidCrystal_I2C lcd(0x27, 16, 2);  // LCD 객체 선언

/* 
 *  여기에서 레시피 비율을 조절할 수 있습니다 
 *  (각 배열마다 총합 개를 맞춰야 칵테일의 양이 일정하게 유지됩니다)
 *  
 *  ex)
 *  
 *  -- 레시피 --
 *  1. 사이다 : 망고주스 : 레몬에이드 = 1 : 3 : 4
 *  1. 사이다 : 망고주스 : 레몬에이드 = 1 : 2 : 1
 *  
 *  1번 모터 사이다 
 *  MOTER 2 // 망고주스
 *  MOTER 3 // 레몬에이드  일 경우,
 *  
 *  int recipe[2][3] = { {1, 3, 4},         // int recipe[레시피 개수][7]
 *                       {2, 4, 2} }
 *  1 + 3 + 4 = 8
 *  2 + 4 + 2 = 0
 */

/*
 * -- 2019 칵테일 레시피 --
 * 
 * 1번 모터 사이다 
 * 2번 모터 망고주스
 * 3번 모터 레몬에이드
 * 4번 모터 밀키스
 * 5번 모터 자몽에이드
 * 6번 모터 오렌지 환타
 * 7번 모터 파워에이드
 * 
 * 1. 사이다 1 + 자몽에이드 1 + 밀키스 1
 * 2. 망고 1 + 사이다 1 + 밀키스 1
 * 3. 망고 1 + 환타 1 + 레몬에이드 1 + 밀키스 1
 * 4. 환타 2 + 파워에이드 1 + 밀키스 1
 * 5. 사이다 2 + 파워에이드 2 + 밀키스 1
 * 6. 자몽에이드 1 + 파워에이드 1
 * 7. 레몬에이드 2 + 망고 1 + 자몽에이드 1 + 환타 1
 * 
 */
 
int recipes[7][7] = { {20, 0, 0, 20, 20, 0, 0},
                     {20, 20, 0, 0, 20, 0, 0},
                     {0, 15, 15, 15, 0, 15 ,0},
                     {0, 0, 0, 15, 0, 30, 15},
                     {24, 0, 0, 12, 0, 0, 24},
                     {0, 0, 0, 0, 30, 0, 30},
                     {0, 12, 24, 0, 12, 12 , 0} };

/* 
 * 여기에서 칵테일의 양을 조절할 수 있습니다
 * volume 값과 칵테일의 양은 정비례합니다
 */
 
 int volume = 100;

void setup() {
  /* 시리얼 창 설정 */
  Serial.begin(9600);
  blueToothSerial.begin(9600);

  /* 핀 모드 설정 */
  pinMode(LED,OUTPUT);
  pinMode(7,OUTPUT);
  pinMode(8,OUTPUT);
  pinMode(9,OUTPUT);
  pinMode(10,OUTPUT);
  pinMode(11,OUTPUT);
  pinMode(12,OUTPUT);
  pinMode(13,OUTPUT);

  /* Lcd 판 기본설정 */
  lcd.begin();
  lcd.backlight();
  lcd.setCursor(0,0);
  lcd.print("OVERFLOW");
  
  Serial.println("-- 아두이노 세팅 완료 --");
}

void loop() {
  if (blueToothSerial.available()){
    char message = blueToothSerial.read();

    if (message >= '0' && message <= '6') {
      Serial.print(message);
      Serial.println(" 칵테일을 만듭니다");
      
      /* 칵테일 만들기 전 LED on */
      digitalWrite(LED,HIGH);
      delay(3000);
      
      setLCDText("MAKING...");
      
      /* 칵테일 만들기 */
      for (int i = 0; i < 7; i++) {
        digitalWrite(i + 7, HIGH);
        delay(volume * recipes[(int) message - 48][i]);
        digitalWrite(i + 7, LOW);
      }
  
      /* 칵테일 만든 후에 LED off */
      delay(1000);
      digitalWrite(LED,LOW);
    
      setLCDText("COMPLETE!");

      /* 
       * 완성메제지 보내기  
       * 최대한 짧게 보내는 게 좋습니다
       */
      blueToothSerial.print("0");
    } else {
      Serial.print(message);
    }
  }
}

void setLCDText(String text) {
    lcd.setCursor(0,1);
    lcd.print(text);
}
