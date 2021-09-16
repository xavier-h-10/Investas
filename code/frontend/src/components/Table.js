import React, { useState } from "react";
import {Text, View} from "react-native";
import * as basicStyle from "../style/basicStyle";

const table={
    header:['时间区间','涨跌幅','排名'],
    data:[['近一周',7.68,21],['近一月',-7.68,21]],
    colorCol:1//begin from zero
}

const Table=(props)=>{
    const [header,setHeader]=useState(props.table.header)
    const [data,setData]=useState(props.table.data)
    const [colorCol,setColorCol]=useState(props.table.header)

    const renderTendencyColor=(value,fontSize)=>{
        if(value>0)
            return <Text style={[fontSize,basicStyle.colorStyle.red]}>{"+"+value.toString()}%</Text>;
        else if(value<0)
            return <Text style={[fontSize,basicStyle.colorStyle.green]}>{value.toString()}%</Text>;
        else
            return <Text style={[fontSize,basicStyle.colorStyle.black]}>{value.toString()}%</Text>;
    }

    const renderHeader=()=>{
        let headersTable=[];
        header.map((it,idx)=>{
            headersTable.push(<Text>{it}</Text>);
        });
        return headersTable;
    }
    
    const renderTable=()=>{
        let table=[];
        let header=renderHeader();
        table.push(<View>{header}</View>);
        data.map((line,idx)=>{
            let lineTmp=[];
            line.map((cell,cellIdx)=>{
                if(cellIdx===colorCol)
                {
                    let coloredText=renderTendencyColor(cell,basicStyle.fontSizeStyle.smallSize);
                    lineTmp.push(coloredText);
                }
                else
                {
                    lineTmp.push(<Text>{cell}</Text>);
                }
            });
            table.push(<View>{lineTmp}</View>);
        });
        return table;
    }
    
    return(
        <View>
            {renderTable()}
        </View>
    );
}

export {Table}