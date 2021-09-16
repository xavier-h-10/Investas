import React from 'react'
import {View, StyleSheet, Text} from 'react-native'
import {TextInput as Input} from 'react-native-paper'
import {theme} from '../../core/theme'
// const input1=React.createRef();
export default function TextInput({errorText, description, ...props}) {
    return (
        <View style={styles.container}>
            <Input
                style={styles.input}
                // selectionColor={theme.colors.primary}
                outlineColor='black'
                selectionColor='black'
                // underlineColor="white"
                // ref={input1}
                mode="flat"
                errorStyle={{color:'red'}}
                {...props}
            />
            {description && !errorText ? (
                <Text style={styles.description}>{description}</Text>
            ) : null}
            {errorText ? <Text style={styles.error}>{errorText}</Text> : null}
        </View>
    )
}

const styles = StyleSheet.create({
    container: {
        width: '100%',
        marginVertical: 12,
    },
    input: {
        backgroundColor: "#fefefe",
        color:'red',
        // borderBottomWidth:1,
        // borderBottomColor: '#d6d6d6',
    },
    description: {
        fontSize: 13,
        // color: theme.colors.secondary,
        paddingTop: 8,
    },
    error: {
        fontSize: 13,
        // color: theme.colors.error,
        paddingTop: 8,
    },
})
