import React, {useState} from 'react'
import {Card, Divider, LinearProgress, Text} from "react-native-elements";
import {Pressable, StyleSheet, View} from "react-native";
import {getStatusBarHeight} from "react-native-status-bar-height";
import message from "react-native-message/index";

function getFontColor(number) {
    if (number === 1) return '#DC143C'
    if (number === 2) return '#FF8C00'
    if (number === 3) return '#FFD700'
    return '#808080'
}

function getBackgroundColor(number) {
    if (number === 1) return '#FFF0F5'
    if (number === 2) return '#FFE4C4'
    if (number === 3) return '#FFF8DC'
    return 'white'
}

export default function Top({number, ...props}) {
    if (number < 1) return null
    if (number > 3) {
        return (
            <View {...props}>
                <Text
                    style={{
                        color: getFontColor(number),
                        backgroundColor: getBackgroundColor(number),
                        textAlign: 'center',
                        fontSize: 15,
                    }}
                >
                    {number}
                </Text>
            </View>
        )
    }
    return (
        <View {...props}>
            <Text style={{color: getFontColor(number), backgroundColor: getBackgroundColor(number)}}>
                {"TOP" + number}
            </Text>
        </View>
    )
}
