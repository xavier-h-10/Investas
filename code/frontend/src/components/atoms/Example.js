import React from 'react';
import type {Node} from 'react';
import {
    SafeAreaView,
    ScrollView,
    StatusBar,
    StyleSheet,
    Text,
    useColorScheme,
    View,
} from 'react-native';

const Example: () => Node = () => {
    return (
        <SafeAreaView>
            <ScrollView
                contentInsetAdjustmentBehavior="automatic">
                <View>
                    <Text>
                        Example here.
                    </Text>
                </View>
            </ScrollView>
        </SafeAreaView>
    );
};
export default Example;
