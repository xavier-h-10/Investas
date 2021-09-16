import React from 'react'
import {Text} from 'react-native-elements'
import ReadMore from "react-native-read-more-text";

function TruncatedFooter(handlePress) {
    return (
        <Text style={{color: '#00AFFF', marginTop: 5}} onPress={handlePress}>
            查看更多
        </Text>
    )
}

function RevealedFooter(handlePress) {
    return (
        <Text style={{color: '#00AFFF', marginTop: 5}} onPress={handlePress}>
            收起
        </Text>
    )
}


export default function MyReadMore(props) {
    return (
        <ReadMore
            {...props}
            renderTruncatedFooter={TruncatedFooter}
            renderRevealedFooter={RevealedFooter}
        >
            {props.component}
        </ReadMore>
    )
}