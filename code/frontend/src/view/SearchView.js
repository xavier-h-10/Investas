import {View, FlatList, Text, SafeAreaView, StatusBar} from "react-native";
import React, { useState } from "react";
import SearchEntry from "../components/SearchEntry";
import { SearchBar } from "react-native-elements";
import { getSimpFundInfo } from "../service/FundService";
import { HeaderBackButton } from "@react-navigation/stack";
import message from "react-native-message/index";
import MyStatusBar from "../components/MyStatusBar";


const FUNDS =
{
    fundName: '工银深证红利ETF联接A',
    fundCode: "481012",
    type: "指数型-股票",
    oneYearRate: -8.22,
    oneDayRate: -0.21,
    NAV: 0.21,
    updateDate: "07-22",
    rate: "3"
}

const ENTRY = {
    fundName: '工银深证红利ETF联接A',
    fundCode: "481012",
    searchStr: "481"
}


const SearchView = ({ navigation }) => {
    const [entries, setEntries] = useState([]);
    const [searchStr, setSearchStr] = useState("");
    const [page, setPage] = useState(0);



    const updateSearch = (str) => {
        if (str === "" || str === null) {
            setEntries([]);
            setSearchStr(str);
            return;
        }
        setEntries([]);
        const setSearchData = (data) => {
            setEntries(data.data.fundSimpArray);
        }
        getSimpFundInfo(str, 0, setSearchData);
        setSearchStr(str);
        setPage(0)
    }

    const renderEntries = ({ item, index }) => {
        return (
            <SearchEntry entry={{ fundName: item.name, fundCode: item.code, searchStr: searchStr }}
                key={index}
            />
        )
    }

    const onEndReached = () => {
        if (searchStr === null || searchStr === "") return
        getSimpFundInfo(searchStr, page + 1, (data) => {
            setEntries(entries.concat(data.data.fundSimpArray));
            // if (data.data.fundSimpArray.length === 0) {
            //     message.info("到达底部")
            // }
        });
        setPage(page + 1)
    }

    return (
        <SafeAreaView>
            {/*<StatusBar*/}
            {/*    animated={true}*/}
            {/*    hidden={false}*/}
            {/*    translucent={false}*/}
            {/*/>*/}
            <MyStatusBar color="#fefefe"/>
            <SearchBar
                placeholder="搜索代码/基金名称"
                lightTheme={true}
                round={true}
                platform={"android"}
                onChangeText={updateSearch}
                value={searchStr}
                searchIcon={<HeaderBackButton onPress={navigation.goBack} />}
                // containerStyle={{height:55}}
                // inputContainerStyle={{height:55}}
                inputStyle={{fontSize:16,marginLeft:0}}
                leftIconContainerStyle={{paddingLeft:0,marginLeft:0}}
            />
            <FlatList
                data={entries}
                renderItem={renderEntries}
                keyExtractor={(item, index) => index}
                onEndReached={onEndReached}
                onEndReachedThreshold={0.2}
                refreshing
            />
        </SafeAreaView>

    )
}


export default SearchView;
