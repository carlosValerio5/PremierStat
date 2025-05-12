import React, {useEffect, useState} from 'react'
import appleStock, {AppleStock} from "@visx/mock-data/lib/mocks/appleStock"
import useMeasure from "react-use-measure"
import {scaleBand, scaleLinear} from "@visx/scale"
import {Group} from "@visx/group"
import {AxisLeft, AxisBottom} from "@visx/axis"
import {Bar} from "@visx/shape"
import {defaultStyles, useTooltip, TooltipWithBounds} from "@visx/tooltip"
import {localPoint} from "@visx/event"
import {TouchEvent, MouseEvent} from "react";

const data = appleStock.slice(0, 10);

const margin = 32;

const defaultWidth = 100;

const defaultHeight = 100;

const getXValue = (d) => d.name;
const getYValue = (d) => d.gls;

const toolTipStyles = {
    ...defaultStyles,
    borderRadius: '8px',
    backgroundColor: "#111",
    color: "white",
    fontFamily: "Space Grotesk",
    fontSize: '14px',
    padding: '8px 12px'
}

const TopScorers = () => {

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

    const innerWidth = width - margin;
    const innerHeight = height - margin;

    const [results, setResults] = React.useState([])
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchPlayer = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/v1/player/top-scorers`);
                const data = await response.json();
                setResults(data); // assuming the API returns an array
            } catch (err) {
                setError("Failed to fetch player data.");
            } finally {
                setLoading(false);
            }
        };

        fetchPlayer();
    }, []);

    if (loading) return <div className="p-4">Loading...</div>;
    if (error) return <div className="p-4 text-red-500">{error}</div>;

    const xScale = scaleBand({
        range: [margin, innerWidth],
        domain: results.map(getXValue),
        padding: 0.2,
    });

    const yValues = results.map(getYValue).filter(value => typeof value === "number");

    const yScale = scaleLinear({
        range: [innerHeight, margin],
        domain: [
            Math.min(...yValues)-1,
            Math.max(...yValues)+1,
        ],
    });

    const isSmallScreen = window.innerWidth < 640;

    return (
        <div>
            <h1 className="text-4xl flex font-bold bg-gradient-to-b from-orange-500 to-red-800 text-transparent bg-clip-text">
                Top Scorers
            </h1>
            <div className="mx-auto grid sm:grid-cols-2 gap-y-2 gap-x-20">
                {results.map((player, index) => (
                    <div key={index} className="w-full mx-auto p-6 bg-neutral-600/50 rounded-xl shadow-md mt-6 text-white
                    outline-orange-400/80 outline-4">
                        <h1 className="text-2xl font-bold mb-4">{player.name}</h1>
                        <div className="grid grid-cols-2 gap-y-0 gap-x-2">
                            <div>
                                <p><strong>Club:</strong> {player.team}</p>
                                <p><strong>Position:</strong> {player.pos}</p>
                                <p><strong>Nationality:</strong> {player.nation}</p>
                                <p><strong>Age:</strong> {player.age}</p>
                            </div>
                            <div>
                                <p><strong>Appearances:</strong> {player.mp}</p>
                                <p><strong>Goals:</strong> {player.gls}</p>
                                <p><strong>Assists:</strong> {player.ast}</p>
                                <p><strong>Minutes:</strong> {player.min}</p>
                            </div>
                        </div>
                    </div>
                ))}
            </div>
            <div className="w-full overflow-x-auto relative">
                <svg
                    ref={ref} width="100%" height="100%" viewBox={`0 0 ${width} ${height}`}
                className="w-full h-[300px] sm:h-[400px] md:h-[500px]"
                >
                    <Group>
                        {results.map((player, index) => {
                            const xValue = getXValue(player);
                            const barWidth = xScale.bandwidth();
                            const barHeight = innerHeight - (yScale(getYValue(player)) ?? 0);
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
                                            tooltipData: player,
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
                        <AxisLeft left={margin} scale={yScale}
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
                        <b>GOALS</b> : {getYValue(tooltipData)}
                    </TooltipWithBounds>
                ) : null}
            </div>
        </div>
    )
}
export default TopScorers
