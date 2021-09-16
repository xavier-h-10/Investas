import React from 'react'
import {Image, StyleSheet} from 'react-native'

export default function Logo() {
    return <Image source={require('../../assets/logo.png')} style={styles.image}/>
}

const styles = StyleSheet.create({
    image: {
        marginTop: 50,
        width: 100,
        height: 100,
        marginBottom: 8,
    },
})
