package com.example.androidthings.myproject;

import com.google.android.things.pio.Gpio;

/**
 * Template for IDD Fall 2017 HW2 (text entry device)
 * Created by bjoern on 9/5/17.
 */

public class Hw2TemplateApp extends SimplePicoPro {
    boolean[] buttons = {HIGH,HIGH,HIGH,HIGH,HIGH,HIGH}; //need space for each button, right now only strum and gpio 128
    int[] frets ={0,0,0}; //sets status of combo of buttons (frets)
    char[] alphabet = (" ACDEFGHIJKLMNOPQRSTUBVWXYZ").toCharArray();

    int[][] alphabetKey= {
        {0,0,0}, //space
        {1,0,0}, //a
        {0,1,0}, //b
        {0,0,1}, //c
        {1,1,0}, //d
        {1,0,1}, //e
        {0,1,1}, //f
        {1,1,1}, //g
        {2,0,0}, //h
        {0,2,0}, //i
        {0,0,2}, //j
        {1,2,0}, //k
        {2,1,0}, //l
        {1,0,2}, //m
        {2,0,1}, //n
        {0,1,2}, //o
        {0,2,1}, //p
        {1,1,2}, //q
        {1,2,1}, //r
        {2,1,1}, //s
        {1,2,2}, //t
        {2,1,2}, //u
        {2,2,1}, //v
        {2,2,0}, //w
        {2,0,2}, //x
        {0,2,2}, //y
        {2,2,2}  //z
    };

    @Override
    public void setup() {
        //set up 1a
        pinMode(GPIO_128,Gpio.DIRECTION_IN);
        setEdgeTrigger(GPIO_128,Gpio.EDGE_BOTH);

        //set up 1b
        pinMode(GPIO_39,Gpio.DIRECTION_IN);
        setEdgeTrigger(GPIO_39,Gpio.EDGE_BOTH);

        //set up 2a
        pinMode(GPIO_37,Gpio.DIRECTION_IN);
        setEdgeTrigger(GPIO_37,Gpio.EDGE_BOTH);

        //set up 2b
        pinMode(GPIO_35,Gpio.DIRECTION_IN);
        setEdgeTrigger(GPIO_35,Gpio.EDGE_BOTH);

        //set up 3a
        pinMode(GPIO_34,Gpio.DIRECTION_IN);
        setEdgeTrigger(GPIO_34,Gpio.EDGE_BOTH);

        //set up 3b
        pinMode(GPIO_33,Gpio.DIRECTION_IN);
        setEdgeTrigger(GPIO_33,Gpio.EDGE_BOTH);

        //set up ENTER
        pinMode(GPIO_32,Gpio.DIRECTION_IN);
        setEdgeTrigger(GPIO_32,Gpio.EDGE_BOTH);

    }

    @Override
    public void loop() {
        //nothing to do here

    }

    @Override
    //triggered whenever edge event occurs (whenever button is pressed)
    //input: gpio pin number and status
    //output: fill fret[] array, read fret array if strummed
    void digitalEdgeEvent(Gpio pin, boolean value) {
        println("digitalEdgeEvent"+pin+", "+value);

        // this is on button button release for pull-up resistors

        //when 'stum' button LOW to HIGH, trigger reading
        if(pin==GPIO_32 && value==HIGH) {
            //printStringToScreen("Strummed");
            fretStatus();
            letterMatch();
        }
        //all following else if statements fill buttons array with status of pins BEFORE 'strum'
        else if(pin==GPIO_128 && value==LOW) {
            buttons[0] = LOW;
        }
        else if(pin==GPIO_128 && value==HIGH) {
            buttons[0] = HIGH;
        }
        else if(pin==GPIO_39 && value==LOW) {
            buttons[1] = LOW;
        }
        else if(pin==GPIO_39 && value==HIGH) {
            buttons[1] = HIGH;
        }
        else if(pin==GPIO_37 && value==LOW) {
            buttons[2] = LOW;
        }
        else if(pin==GPIO_37 && value==HIGH) {
            buttons[2] = HIGH;
        }
        else if(pin==GPIO_35 && value==LOW) {
            buttons[3] = LOW;
        }
        else if(pin==GPIO_35 && value==HIGH) {
            buttons[3] = HIGH;
        }
        else if(pin==GPIO_34 && value==LOW) {
            buttons[4] = LOW;
        }
        else if(pin==GPIO_34 && value==HIGH) {
            buttons[4] = HIGH;
        }
        else if(pin==GPIO_33 && value==LOW) {
            buttons[5] = LOW;
        }
        else if(pin==GPIO_33 && value==HIGH) {
            buttons[5] = HIGH;
        }
    }

    //create fret array based on buttons [] status
    void fretStatus() {
        //determine how many buttons pressed in fret 1
        if (!buttons[0] && !buttons[1]) {
            frets[0] = 2;
        } else if (buttons[0] && buttons[1]) {
            frets[0] = 0;
        } else {
            frets[0] = 1;
        }

        //determine how many buttons pressed in fret 2
        if (!buttons[2] && !buttons[3]) {
            frets[1] = 2;
        } else if (buttons[2] && buttons[3]) {
            frets[1] = 0;
        } else {
            frets[1] = 1;
        }

        //determine how many buttons pressed in fret 3
        if (!buttons[4] && !buttons[5]) {
            frets[2] = 2;
        } else if (buttons[4] && buttons[5]) {
            frets[2] = 0;
        } else {
            frets[2] = 1;
        }
    }

    void letterMatch(){
        boolean foundMatch = false;
        while (!foundMatch){
            for (int row =0; row<27; row++) {
                if (alphabetKey[row][0] == frets [0] && alphabetKey[row][1] == frets [1] && alphabetKey[row][2]==frets[2]) {
                    printCharacterToScreen(alphabet[row]);
                    foundMatch = true;
                    row=27;
                }
                else{
                    printCharacterToScreen(' ');
                }
            }
        }
    }


    /*
    void digitalEdgeEvent (Gpio pin, boolean value) {
        //if this was the "strum" pin:
        //now read the whole array and output a character based on the button state of all other buttons
        //map from pin to a number betwen 0 and 6
        int num;
        if(pin==GPIO_39) num=5;

        frets[num]=value;
        //else - this was a "fret" pin:
        update the internal array to write the new value into it
    }
    */


}
