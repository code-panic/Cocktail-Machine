#include <SoftwareSerial.h>

SoftwareSerial BTSerial(2, 3);   //bluetooth module Tx:Digital 2 Rx:Digital 3

void setup() {
  Serial.begin(9600);
  BTSerial.begin(9600);
  Serial.println("ATcommand");  //ATcommand Start
}

void loop() {
  if (BTSerial.available())
    Serial.write(BTSerial.read());
  if (Serial.available())
    BTSerial.write(Serial.read());
}
