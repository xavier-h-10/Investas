import React from 'react'
import {Snackbar} from 'react-native-paper'
import {StyleSheet, View, Text} from 'react-native'
import {getStatusBarHeight} from 'react-native-status-bar-height'
import {theme} from '../../core/theme'

export default function Toast({type = 'error', message, onDismiss, duration = 1500}) {
    return (
        <View style={styles.container}>
            <Snackbar
                visible={!!message}
                duration={duration}
                onDismiss={onDismiss}
                style={{
                    backgroundColor:
                        type === 'error' ? theme.colors.error : theme.colors.success,
                }}
            >
                <Text style={styles.content}>{message}</Text>
            </Snackbar>
        </View>
    )
}

const styles = StyleSheet.create({
    container: {
        position: 'absolute',
        top: 80 + getStatusBarHeight(),
        width: '100%',
    },
    content: {
        fontWeight: '500',
    },
})
