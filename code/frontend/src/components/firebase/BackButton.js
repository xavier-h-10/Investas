import React from 'react'
import {TouchableOpacity, Image, StyleSheet} from 'react-native'
import {getStatusBarHeight} from 'react-native-status-bar-height'
import Ionicons from "react-native-vector-icons/Ionicons";
import {Button} from "react-native-elements";

export default function BackButton({goBack}) {
    return (
        <TouchableOpacity onPress={goBack} style={styles.container}>
            <Image
                style={styles.image}
                source={require('../../assets/arrow_back.png')}
            />
            {/*<Button*/}
            {/*    buttonStyle={styles.buttonContainer}*/}
            {/*    icon={*/}
            {/*        <Ionicons*/}
            {/*            name="chevron-back-outline"*/}
            {/*            size={26}*/}
            {/*        />*/}
            {/*    }*/}
            {/*    onPress={() => {*/}
            {/*        navigation.goBack();*/}
            {/*    }}*/}
            {/*/>*/}
        </TouchableOpacity>
    )
}

const styles = StyleSheet.create({
    container: {
        position: 'absolute',
        top: 10 + getStatusBarHeight(),
        left: 5,
    },
    image: {
        width: 24,
        height: 24,
    },
    buttonContainer: {
        backgroundColor: '#fefefe',
    },
})
