void setup() {
  /* 시리얼 창 설정 */
  Serial.begin(9600);

  pinMode(5, OUTPUT);

  for (int i = 7; i <= 13; i++ ) {
    pinMode(i,OUTPUT);
  }
  
  Serial.println("-- 아두이노 세팅 완료 --");
}

void loop() { 
  motorTest(10);
//  motorTest(9); 
// motorTest(108;

// for (int i = 7; i <= 13; i++ ) {
//    motorTest(i);
//  }
}

void motorTest (int num) {
  int moterNum = num - 6;
  Serial.print(moterNum);
  Serial.print("번 모터 테스트\n");
  
  digitalWrite(num, HIGH);
  delay(1500);
  digitalWrite(num, LOW);
}
