import {Dimensions, TouchableOpacity, View} from "react-native";
import React, {useState} from "react";
import * as basicStyle from "../style/basicStyle";
import {ListItem, Text} from "react-native-elements";
import Icon from 'react-native-vector-icons/dist/FontAwesome';
import {useNavigation} from "@react-navigation/native";

const SearchEntry = (props) => {
    const {fundCode, fundName, searchStr, key} = props.entry
    const navigation = useNavigation();

    const renderSearchText = () => {
        if (fundCode == null && fundName == null)
            return <Text>{searchStr}</Text>
        let text = fundCode + " " + fundName;
        let idxBegin = text.indexOf(searchStr);
        let fontStr = text.substring(0, idxBegin);
        let endStr = text.substring(idxBegin + searchStr.length, text.length);

        return (
            <Text style={basicStyle.fontSizeStyle.searchSmallSize}>
                <Text style={basicStyle.colorStyle.black}>
                    {fontStr}
                </Text>
                <Text style={basicStyle.colorStyle.blue}>
                    {searchStr}
                </Text>
                <Text style={basicStyle.colorStyle.black}>
                    {endStr}
                </Text>
            </Text>
        );
    }

    return (
        <ListItem key={key}>
            <Icon name="search" size={15} color="grey"/>
            <ListItem.Content>
                <TouchableOpacity
                    onPress={() => {navigation.navigate('Fund', {code: fundCode,name:fundName})}}
                >
                    {renderSearchText()}
                </TouchableOpacity>
            </ListItem.Content>
            <ListItem.Chevron/>
        </ListItem>
    )
}

export default SearchEntry;
