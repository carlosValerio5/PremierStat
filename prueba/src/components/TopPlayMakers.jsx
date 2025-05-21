import {useEffect, useState} from "react";
import { BarStackHorizontal } from "@visx/shape";
import { Group } from "@visx/group";
import { AxisBottom, AxisLeft } from "@visx/axis";
import { scaleBand, scaleLinear, scaleOrdinal } from "@visx/scale";
import { withTooltip,Tooltip, defaultStyles} from "@visx/tooltip";
import { LegendOrdinal } from "@visx/legend"

const orange1 = "#FBC02D";
const orange2 = "#FF9800";
export const orange3 = "#d08700"
const defaultMargin = { top: 40, left: 150, right: 20, bottom: 100};
const tooltipStyles = {
    ...defaultStyles,
    minWidth: 60,
    backgroundColor: "rgba(0,0,0,0.9",
    color: "white",
};

let tooltipTimeout;

const keys=["xag", "ast", "prgp"];
const colors = [orange1, orange2, orange3];

const TopPlayMakers = ({
                           width = 800,
                           height = 500,
                           tooltipOpen,
                           tooltipData,
                           tooltipLeft,
                           tooltipTop,
                           showTooltip,
                           hideTooltip,
                       }) => {

    const [playMakers, setPlayMakers] = useState([])

    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchData = async () => {
            try{
                const response = await fetch(`${import.meta.env.VITE_API_URL}/api/v1/player/top-playmakers`)
                const data = await response.json();
                setPlayMakers(data)
            }catch(error){
                setError("Failed to fetch data");
            }finally {
                setLoading(false);
            }
        }
        fetchData();
    }, [])


    if(loading) return <div className="p-4">Loading...</div>;
    if (error) return <div className="p-4 text-red-500">{error}</div>;

    const xMax = 800 - defaultMargin.left - defaultMargin.right;
    const yMax = height - defaultMargin.top - defaultMargin.bottom;



    const normalizedPlayMakers = playMakers.map((d) => ({
        ...d,
        xag: parseFloat(d.zXag) || 0,
        ast: parseFloat(d.zAssists) || 0,
        prgp: parseFloat(d.zProgressivePasses) || 0,
    }));


    const scoreScale = scaleLinear({
        domain: [
            0,
            Math.max(
                ...normalizedPlayMakers.map((d) =>
                    keys.reduce((sum, key) => sum + d[key], 0)
                )
            ),
        ],
        range: [0, xMax],
        nice: true,
    });


    const nameScale = scaleBand({
        domain: playMakers.map((p) => p.name),
        range: [0, yMax],
        padding: 0.2,
    });

    const colorScale = scaleOrdinal({
        domain: keys,
        range: colors,
    })


    return (
        <div className="mt-20">
            <h1 className="text-4xl flex font-bold bg-gradient-to-b from-orange-500 to-red-800
            text-transparent bg-clip-text">Top PlayMakers</h1>
            <div className="mx-auto grid sm:grid-cols-2 gap-y-2 gap-x-20">
                {playMakers.map((player) => (
                    <div key={player.name} className="w-full mx-auto p-6 bg-neutral-600/50 rounded-xl shadow-md mt-6
                    text-white outline-orange-400/80 outline-4">
                        <h1 className="text-2xl font-bold mb-4">{player.name}</h1>
                        <div className="grid grid-cols-2 gap-y-0 gap-x-2">
                            <>
                                <p><strong>Club: </strong>{player.team}</p>
                                <p><strong>Position: </strong>{player.pos}</p>
                                <p><strong>Nationality: </strong>{player.nation}</p>
                                <p><strong>Age: </strong>{player.age}</p>
                            </>
                            <>
                                <p><strong>Appearances: </strong>{player.mp}</p>
                                <p><strong>Goals: </strong>{player.gls}</p>
                                <p><strong>Assists: </strong>{player.ast}</p>
                                <p><strong>Minutes: </strong>{player.min}</p>
                            </>
                        </div>
                    </div>
                ))}
            </div>

            {/*BarChart*/}
            <div className="relative flex flex-col justify-center w-full overflow-x-auto">
                <svg width={Math.max(width, 600)} height={height}>
                    <Group top={defaultMargin.top} left={defaultMargin.left}>
                        <BarStackHorizontal
                            data={normalizedPlayMakers}
                            keys={keys}
                            height={yMax}
                            y={(d)=>d.name}
                            xScale={scoreScale}
                            yScale={nameScale}
                            color={colorScale}
                        >
                            {(barStacks)=>
                            barStacks.map((barStack)=>
                            barStack.bars.map((bar)=>(
                                <rect
                                key={`barstack-horizontal-${barStack.index}-${bar.index}`}
                                x={bar.x}
                                y={bar.y}
                                width={bar.width}
                                height={bar.height}
                                fill={bar.color}
                                onMouseLeave={()=>{
                                    tooltipTimeout=window.setTimeout(()=>{
                                        hideTooltip();
                                    },300);
                                }}
                                onMouseMove={(event)=>{
                                    if (tooltipTimeout) clearTimeout(tooltipTimeout);
                                    const eventSvg = event.currentTarget.ownerSVGElement;
                                    const {x, y} = eventSvg.getBoundingClientRect();
                                    showTooltip({
                                        tooltipData: bar,
                                        tooltipTop: event.clientY-y,
                                        tooltipLeft: event.clientX-x,
                                    });
                                }}
                                />
                            ))
                            )
                            }
                        </BarStackHorizontal>
                        <AxisLeft
                            hideAxisLine
                            hideTicks
                            scale={nameScale}
                            stroke={orange3}
                            tickStroke={orange3}
                            tickLabelProps={{
                                fill: "#f8fafc",
                                fontSize: 16,
                                textAnchor: 'end',
                                dy: '0.33em',
                                dx: '-10px'
                            }}
                        />
                        <AxisBottom
                            top={yMax}
                            scale={scoreScale}
                            stroke={orange3}
                            tickStroke={orange3}
                            tickLabelProps={{
                                fill: orange3,
                                fontSize: 14,
                                textAnchor: 'middle',
                            }}
                        />
                    </Group>
                </svg>
                <div
                    className="
                    mt-4
                    flex
                    justify-center
                    text-sm
                    ">
                    <LegendOrdinal scale={colorScale} direction="row" labelMargin="0 15px 0 0" />
                </div>
                {tooltipOpen && tooltipData && (
                    <Tooltip top={tooltipTop} left={tooltipLeft} style={tooltipStyles}>
                        <div style={{color: colorScale(tooltipData.key)}}>
                            <strong>{tooltipData.key}</strong>
                        </div>
                        <div>{tooltipData.bar.data[tooltipData.key]}</div>
                    </Tooltip>
                )}
            </div>
        </div>
    )
}
export default withTooltip(TopPlayMakers);
