import React from 'react'
import {Pressable, StyleSheet, TouchableOpacity, View} from 'react-native'
import {Text} from 'react-native-paper'
import {useNavigation} from "@react-navigation/native";

export default function GoToFundView({code, name, pressed = false, push = false, children}) {
    const navigation = useNavigation()
    const onPress = () => {
        if (push) {
            navigation.push("Fund", {code: code, name: name})
        } else {
            navigation.navigate("Fund", {code: code, name: name})
        }
    }
    if (pressed) {
        return <TouchableOpacity onPress={onPress}>{children}</TouchableOpacity>
    } else {
        return <Pressable onPress={onPress}>{children}</Pressable>
    }
}
