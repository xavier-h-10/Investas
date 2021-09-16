import {StyleSheet} from "react-native";

const fontSizeStyle=StyleSheet.create({
    searchSmallSize:{
        fontSize:17,
    },

    smallSize:{
        fontSize:20,
    },

    mediumSize:{
        fontSize:30,
        //35->30  20210911
    },
})

const fontWeightStyle=StyleSheet.create({
    boldWeight:{
        fontWeight:'bold',
    },
})

const colorStyle=StyleSheet.create({
    green:{
        color:'#36ab8c',
    },
    red:{
        color:'#eb394a',
    },
    black:{
        color:'black',
    },
    blue:{
        color:'blue',
    },
})

const textStyle=StyleSheet.create({

    tinyGrey:{
        color:'grey',
        fontSize:12,
    },

    tinyBlueBold:{
        color:'blue',
        fontSize:12,
    },

    smallBlackBold:{
        color:'black',
        fontWeight:'bold',
        fontSize:20,
    },

    smallBlueBold:{
        color:'blue',
        fontWeight:'bold',
        fontSize:20,
    },

    smallGrey:{
        color:'grey',
        fontSize:20,
    },


    mediumRedBold:{
        color:'red',
        fontWeight:'bold',
        fontSize:35,
    },

    mediumBlackBold:{
        color:'black',
        fontWeight:'bold',
        fontSize:35,
    },

    mediumGreenBold:{
        color:'green',
        fontWeight:'bold',
        fontSize:35,
    },
});

const layoutStyle=StyleSheet.create({
    row: {
        flexDirection: "row",
        flexWrap: "wrap",
        alignItems: "center",
        justifyContent:'space-between',
    },
    spaceFreeColumn: {
        flexDirection: "column",
        flexWrap: "wrap",
        alignItems: "center",
    }
});

export {fontSizeStyle,fontWeightStyle,layoutStyle,colorStyle,textStyle};
