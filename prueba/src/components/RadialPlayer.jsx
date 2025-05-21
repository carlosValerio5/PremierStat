import React from 'react'
import { Group } from '@visx/group';
import { scaleLinear } from '@visx/scale';
import { Point } from '@visx/point';
import { Line, LineRadial } from '@visx/shape';
import useMeasure from "react-use-measure";
import {defaultStyles, useTooltip, TooltipWithBounds} from "@visx/tooltip"
import {localPoint} from "@visx/event";


const defaultWidth = 100;

const defaultHeight = 100;

const orange = '#ff9933';
export const pumpkin = '#f5810c';
const silver = '#d9d9d9';
export const background = 'oklch(26.9% 0 0)';

const degrees = 360;

const y = (d) => d.frequency;

const genAngles = (length) =>
    [...new Array(length + 1)].map((_, i) => ({
        angle: i * (degrees / length) + (length % 2 === 0 ? 0 : degrees / length / 2),
    }));

const genPoints = (length, radius) => {
    const step = (Math.PI * 2) / length;
    return [...new Array(length)].map((_, i) => ({
        x: radius * Math.sin(i * step),
        y: radius * Math.cos(i * step),
    }));
};

function genPolygonPoints(
    dataArray,
    scale,
    getValue,
) {
    const step = (Math.PI * 2) / dataArray.length;
    const points = [];
    let pointString = "";

    for (let i = 0; i < dataArray.length; i++) {
        const index = i % dataArray.length; // ensures wrap-around for closing the polygon
        const value = getValue(dataArray[index]);
        const xVal = scale(value) * Math.sin(i * step);
        const yVal = scale(value) * Math.cos(i * step);

        points.push({ x: xVal, y: yVal, label: dataArray[index].letter, value: dataArray[index].frequency });
        pointString += `${xVal},${yVal} `;
    }

    return { points, pointString: pointString.trim() };
}

const defaultMargin = { top: 40, left: 80, right: 80, bottom: 80 };

const toolTipStyles = {
    ...defaultStyles,
    borderRadius: '8px',
    backgroundColor: "#111",
    color: "white",
    fontFamily: "Space Grotesk",
    fontSize: '14px',
    padding: '8px 12px'
}

//Funcion para restringir valores a un rango establecido para visualizar de mejor manera
const clampZscore= (value, min=-10, max=10) => {
    return Math.max(min, Math.min(value, max));
}

const RadialPlayer = ({player, levels = 5, margin = defaultMargin }) => {
    if (!player) return null;

    const [ref, bounds] = useMeasure();

    const width = bounds.width || defaultWidth;
    const height = bounds.height || defaultHeight;


    const data = [
        {letter: "Assists", frequency: clampZscore(player.zAssists)},
        {letter: "Xag", frequency: clampZscore(player.zXag)},
        {letter: "Progressive Passes", frequency: clampZscore(player.zProgressivePasses)},
        {letter: "Goals", frequency: clampZscore(player.zGoals)},
        {letter: "XG", frequency: clampZscore(player.zXg)}
    ]

    const {
        showTooltip,
        hideTooltip,
        tooltipData,
        tooltipLeft = 0,
        tooltipTop = 0
    } = useTooltip();

    const xMax = width - margin.left - margin.right;
    const yMax = height - margin.top - margin.bottom;
    const radius = Math.min(xMax, yMax) / 2;

    const radialScale = scaleLinear({
        range: [0, Math.PI * 2],
        domain: [degrees, 0],
    });

    const yScale = scaleLinear({
        range: [0, radius],
        domain: [-10, 10],
    });

    const webs = genAngles(data.length);
    const points = genPoints(data.length, radius);
    const polygonPoints = genPolygonPoints(data, (d) => yScale(d) ?? 0, y);
    const zeroPoint = new Point({ x: 0, y: 0 });


    return width < 10 ? null : (
        <div ref={ref} className="w-full mt-10 relative">
            <svg width="100%" height="100%" viewBox={`0 0 ${width} ${height}`}>
                <rect fill={background} width={width} height={height} rx={14} />
                <Group top={height / 2 - margin.top} left={width / 2}>
                    {[...new Array(levels)].map((_, i) => (
                        <LineRadial
                            key={`web-${i}`}
                            data={webs}
                            angle={(d) => radialScale(d.angle) ?? 0}
                            radius={((i + 1) * radius) / levels}
                            fill="none"
                            stroke={silver}
                            strokeWidth={2}
                            strokeOpacity={0.8}
                            strokeLinecap="round"
                        />
                    ))}
                    {[...new Array(data.length)].map((_, i) => (
                        <Line key={`radar-line-${i}`} from={zeroPoint} to={points[i]} stroke={silver} />
                    ))}
                    <polygon
                        points={polygonPoints.pointString}
                        fill={orange}
                        fillOpacity={0.3}
                        stroke={orange}
                        strokeWidth={1}
                    />
                    {polygonPoints.points.map((point, i) => (
                        <circle key={`radar-point-${i}`} cx={point.x} cy={point.y} r={4} fill={pumpkin}
                                onMouseMove={(event) => {
                                    const coords = localPoint(event);

                                    if(!data[i]) return null;
                                    if (!point) return;

                                    showTooltip({
                                        tooltipData: {
                                            label: data[i].letter,
                                            value: data[i].frequency,
                                        },
                                        tooltipLeft: coords.x,
                                        tooltipTop: coords.y,
                                    });
                                }}
                                onMouseLeave={() => hideTooltip()}/>
                    ))}
                </Group>
            </svg>
            {tooltipData ? (
                <TooltipWithBounds
                    key={Math.random()}
                    top={tooltipTop}
                    left={tooltipLeft}
                    style={toolTipStyles}
                >
                    <b>{tooltipData.label}</b>
                </TooltipWithBounds>
            ) : null}
        </div>
    );
}
export default RadialPlayer
