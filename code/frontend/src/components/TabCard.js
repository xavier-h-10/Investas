import React, {useState} from 'react'
import {Card, Divider, LinearProgress, Text} from "react-native-elements";
import {Pressable, StyleSheet, View} from "react-native";
import {getStatusBarHeight} from "react-native-status-bar-height";
import message from "react-native-message/index";

export default function TabCard(props) {
    const {content} = props
    const [selected, setSelected] = useState(0)
    return (
        <Card containerStyle={{borderRadius: 10, borderWidth:0}}>
            <View style={{flexDirection: 'row', alignItems: 'center'}}>
                {
                    content.map((item, index) => (
                        <View style={{marginRight: 25}} key={index}>
                            <Pressable
                                onPress={() => {
                                    if (selected !== index && props.onChange !== undefined) {
                                        props.onChange(index)
                                    }
                                    setSelected(index)
                                }}
                            >
                                <Text style={index === selected ? styles.textSelected : styles.textUnselected}>
                                    {item.header}
                                </Text>
                                {
                                    index === selected ?
                                        <LinearProgress variant={'determinate'} color={'#0060FF'} value={1}/> : null
                                }
                            </Pressable>
                        </View>
                    ))
                }
            </View>
            <View style={{marginTop: 15}}>
                {
                    content[selected].body
                }
            </View>
        </Card>
    )
}

const styles = StyleSheet.create({
    textSelected: {
        color: '#0080FF',
        fontSize: 17,
        // fontWeight: 'bold',
    },
    textUnselected: {
        color: '#000000',
        fontSize: 17,
        // fontWeight: 'bold',
    },
})
