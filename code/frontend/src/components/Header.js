import React from 'react'
import {StyleSheet, useWindowDimensions, View} from 'react-native'
import {Text} from 'react-native-paper'

export default function Header(props) {
    const window = useWindowDimensions()
    return (
        <View style={[styles.headerView, {height: window.height * 0.06}]}>
            <View style={{width: '20%'}}>
                {
                    props.headerLeft ? props.headerLeft : null
                }
            </View>
            <View style={{width: '60%'}}>
                <Text style={styles.headerText} {...props} />
            </View>
            <View style={{width: '20%', flexDirection: 'row-reverse', justifyContent: 'space-around'}}>
                {
                    props.headerRight ? props.headerRight : null
                }
            </View>
        </View>
    )
}

const styles = StyleSheet.create({
    headerView: {
        flexDirection: 'row',
        alignItems: 'center',
        // backgroundColor: '#FFFFFF',
        backgroundColor:'#2867ff',
        // marginTop:10,
    },
    headerText: {
        fontSize: 18,
        // fontWeight: 'bold',
        paddingVertical: 12,
        alignSelf: 'center',
        color:'#ffffff',
    },
})
