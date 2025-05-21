import { ShieldHalf } from "lucide-react";
import { Ruler } from "lucide-react";
import { ChartColumnStacked } from "lucide-react";
import { History } from "lucide-react";

import user1 from "../assets/profile-pictures/user1.jpg";
import user2 from "../assets/profile-pictures/user2.jpg";
import user3 from "../assets/profile-pictures/user3.jpg";
import user4 from "../assets/profile-pictures/user4.jpg";
import user5 from "../assets/profile-pictures/user5.jpg";
import user6 from "../assets/profile-pictures/user6.jpg";

export const navItems = [
  { label: "Players", href: "/players" },
  { label: "Teams", href: "/teams" },
  { label: "Matches", href: "/matches" },
  { label: "Compare", href: "/compare" },
];


export const features = [
  {
    icon: <Ruler />,
    text: "Detailed Players information",
    description:
        "Follow any player’s journey through the season no replays required. Get detailed stats, match-by-match performance that tell the full story without ever missing a moment.",
  },
  {
    icon: <ShieldHalf />,
    text: "Teams Information",
    description:
        "Dive into in-depth season stats and see how your favorite team has performed, track wins, losses, goals, and more, all in one place.",
  },
  {
    icon: <ChartColumnStacked />,
    text: "Compare Players",
    description:
        "Compare two players side by side and discover who’s the better fit for your playstyle, analyze stats, strengths, and key attributes that matter most to you.",
  },
  {
    icon: <History />,
    text: "Previous Match Data",
    description:
        "Look back at previous games and their outcomes, relive key moments, check scores, and explore match stats from every round of the season.",
  },
];