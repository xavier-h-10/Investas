import React from "react";
import {Text, View, StyleSheet, Dimensions} from "react-native";
import Spinner from "react-native-spinkit";

export default function Loading() {
    return (
        <View style={styles.container}>
            <Spinner style={styles.spinner} type='WanderingCubes' color={'#0060FF'}/>
        </View>
    )
}

const height=Dimensions.get("window").height;

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        marginTop: height*0.4,
    },
    spinner: {
        marginBottom: 50,
    },
});
