import React from 'react'
import useMeasure from "react-use-measure"
import {scaleBand, scaleLinear} from "@visx/scale"
import {Group} from "@visx/group"
import {AxisLeft, AxisBottom} from "@visx/axis"
import {Bar} from "@visx/shape"
import {defaultStyles, useTooltip, TooltipWithBounds} from "@visx/tooltip"
import {localPoint} from "@visx/event"

const margin = 32;

const defaultWidth = 100;

const defaultHeight = 100;

const getXValue = (d) => d.label;
const getYValue = (d) => d.value;


const toolTipStyles = {
    ...defaultStyles,
    borderRadius: '8px',
    backgroundColor: "#111",
    color: "white",
    fontFamily: "Space Grotesk",
    fontSize: '14px',
    padding: '8px 12px'
}

const PlayerBarChart = ({player, type}) => {

    if(!player) return null;
    if(!type) return null;

    let data = [];

    if(!type.toLowerCase().localeCompare("goals")) {
        data = [
            {label: "Goals", value: player.gls},
            {label: "Xg", value: player.xg},
            {label: "NpXg", value: player.npxg}
        ];
    }else if (!type.toLowerCase().localeCompare("assists")) {
        data = [
            {label: "Assists", value: player.ast},
            {label: "XAg", value: player.xag},
        ]
    }else {
        return null;
    }


    const [ref, bounds] = useMeasure();
    const {
        showTooltip,
        hideTooltip,
        tooltipData,
        tooltipLeft = 0,
        tooltipTop = 0
    } = useTooltip();

    const width = bounds.width || defaultWidth;
    const height = bounds.height || defaultHeight;

    const innerWidth = width - margin*2;
    const innerHeight = height - margin*2;

    const xScale = scaleBand({
        range: [margin, innerWidth],
        domain: data.map(getXValue),
        padding: 0.2,
    });

    const yMax = Math.max(...data.map(getYValue));
    const yMin = Math.min(...data.map(getYValue));
    const yScale = scaleLinear({
        range: [innerHeight, margin],
        domain: [
            yMin-1,
            yMax+1
        ],
    });

    const isSmallScreen = window.innerWidth < 640;


    return (
        <div className="bg-neutral-600/30 rounded-2xl mt-10 w-3xl flex justify-center items-center">
            <div className="w-full flex justify-center overflow-x-auto backdrop-blur-3xl border-b border-neutral-700/80 rounded-2xl">
                <svg
                    ref={ref} width="100%" height="100%" viewBox={`0 0 ${width} ${height}`}
                    className="ml-15 h-[250px] sm:h-[300px] md:h-[400px]"
                >
                    <Group>
                        {data.map((d) => {
                            const xValue = getXValue(d);
                            const barWidth = xScale.bandwidth();
                            const barHeight = innerHeight - (yScale(getYValue(d)) ?? 0);
                            const barX = xScale(xValue);
                            const barY = innerHeight - barHeight;

                            return (
                                <Bar
                                    key={`bar-${xValue}`}
                                    x={barX}
                                    y={barY}
                                    width={barWidth}
                                    height={barHeight}
                                    fill={"orange"}
                                    onMouseMove={(event) => {
                                        const point = localPoint(event.target.ownerSVGElement, event);

                                        if (!point) return;

                                        showTooltip({
                                            tooltipData: d,
                                            tooltipLeft: point.x,
                                            tooltipTop: point.y,
                                        });
                                    }}
                                    onMouseLeave={() => hideTooltip()}
                                />
                            );
                        })}


                    </Group>
                    <Group>
                        <AxisBottom top={innerHeight} scale={xScale}
                                    tickLabelProps={()=>({
                                        fill: "white",
                                        fontSize: (isSmallScreen)?8:20,
                                        textAnchor: "middle",
                                    })}/>
                    </Group>

                    <Group>
                        <AxisLeft left={margin+10} scale={yScale}
                                  tickLabelProps={()=>({
                                      fill: "white",
                                      textAnchor: "end",
                                  })}/>
                    </Group>
                </svg>
                {tooltipData ? (
                    <TooltipWithBounds
                        key={Math.random()}
                        top={tooltipTop}
                        left={tooltipLeft}
                        style={toolTipStyles}
                    >
                        <b>{tooltipData.label}</b> : {tooltipData.value}
                    </TooltipWithBounds>
                ) : null}
            </div>
        </div>
    )
}
export default PlayerBarChart
