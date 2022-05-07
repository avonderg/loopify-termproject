package edu.brown.cs.student.main.JsonParsing;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.JSONObject;

public class JsonConstant {
  public static final String json = "{\n" +
      "  \"geocoded_waypoints\":\n" +
      "    [\n" +
      "      {\n" +
      "        \"geocoder_status\": \"OK\",\n" +
      "        \"place_id\": \"ChIJ7cv00DwsDogRAMDACa2m4K8\",\n" +
      "        \"types\": [\"locality\", \"political\"],\n" +
      "      },\n" +
      "      {\n" +
      "        \"geocoder_status\": \"OK\",\n" +
      "        \"place_id\": \"ChIJ69Pk6jdlyIcRDqM1KDY3Fpg\",\n" +
      "        \"types\": [\"locality\", \"political\"],\n" +
      "      },\n" +
      "      {\n" +
      "        \"geocoder_status\": \"OK\",\n" +
      "        \"place_id\": \"ChIJgdL4flSKrYcRnTpP0XQSojM\",\n" +
      "        \"types\": [\"locality\", \"political\"],\n" +
      "      },\n" +
      "      {\n" +
      "        \"geocoder_status\": \"OK\",\n" +
      "        \"place_id\": \"ChIJE9on3F3HwoAR9AhGJW_fL-I\",\n" +
      "        \"types\": [\"locality\", \"political\"],\n" +
      "      },\n" +
      "    ],\n" +
      "  \"routes\":\n" +
      "    [\n" +
      "      {\n" +
      "        \"bounds\":\n" +
      "          {\n" +
      "            \"northeast\": { \"lat\": 41.8781139, \"lng\": -87.6297872 },\n" +
      "            \"southwest\": { \"lat\": 34.0523523, \"lng\": -118.2435731 },\n" +
      "          },\n" +
      "        \"copyrights\": \"Map data ©2021 Google, INEGI\",\n" +
      "        \"legs\":\n" +
      "          [\n" +
      "            {\n" +
      "              \"distance\": { \"text\": \"579 mi\", \"value\": 932330 },\n" +
      "              \"duration\": { \"text\": \"8 hours 41 mins\", \"value\": 31265 },\n" +
      "              \"end_address\": \"Joplin, MO, USA\",\n" +
      "              \"end_location\": { \"lat\": 37.0842449, \"lng\": -94.513284 },\n" +
      "              \"start_address\": \"Chicago, IL, USA\",\n" +
      "              \"start_location\": { \"lat\": 41.8781139, \"lng\": -87.6297872 },\n" +
      "              \"steps\":\n" +
      "                [\n" +
      "                  {\n" +
      "                    \"distance\": { \"text\": \"443 ft\", \"value\": 135 },\n" +
      "                    \"duration\": { \"text\": \"1 min\", \"value\": 26 },\n" +
      "                    \"end_location\": { \"lat\": 41.8769003, \"lng\": -87.6297353 },\n" +
      "                    \"html_instructions\": \"Head <b>south</b> on <b>S Federal St</b> toward <b>W Van Buren St</b>\",\n" +
      "                    \"polyline\": { \"points\": \"eir~FdezuOdCEjBC\" },\n" +
      "                    \"start_location\": { \"lat\": 41.8781139, \"lng\": -87.6297872 },\n" +
      "                    \"travel_mode\": \"DRIVING\",\n" +
      "                  },\n" +
      "                  {\n" +
      "                    \"distance\": { \"text\": \"0.3 mi\", \"value\": 522 },\n" +
      "                    \"duration\": { \"text\": \"1 min\", \"value\": 88 },\n" +
      "                    \"end_location\": { \"lat\": 34.0515659, \"lng\": -118.2424561 },\n" +
      "                    \"html_instructions\": \"Turn <b>left</b> onto <b>N Los Angeles St</b>\",\n" +
      "                    \"maneuver\": \"turn-left\",\n" +
      "                    \"polyline\":\n" +
      "                      {\n" +
      "                        \"points\": \"glznEnqtpUEFEH|@t@HJJHJHf@b@BBXX\\\\\\\\d@d@LJt@p@`A~@~AjAtAbAXRp@d@x@l@|@v@\",\n" +
      "                      },\n" +
      "                    \"start_location\":\n" +
      "                      { \"lat\": 34.0552449, \"lng\": -118.2391171 },\n" +
      "                    \"travel_mode\": \"DRIVING\",\n" +
      "                  },\n" +
      "                  {\n" +
      "                    \"distance\": { \"text\": \"377 ft\", \"value\": 115 },\n" +
      "                    \"duration\": { \"text\": \"1 min\", \"value\": 32 },\n" +
      "                    \"end_location\": { \"lat\": 34.0522387, \"lng\": -118.2433984 },\n" +
      "                    \"html_instructions\": \"Turn <b>right</b> onto <b>E 1st St</b>\",\n" +
      "                    \"maneuver\": \"turn-right\",\n" +
      "                    \"polyline\": { \"points\": \"iuynEjfupUU^q@bA}@vA\" },\n" +
      "                    \"start_location\":\n" +
      "                      { \"lat\": 34.0515659, \"lng\": -118.2424561 },\n" +
      "                    \"travel_mode\": \"DRIVING\",\n" +
      "                  },\n" +
      "                  {\n" +
      "                    \"distance\": { \"text\": \"66 ft\", \"value\": 20 },\n" +
      "                    \"duration\": { \"text\": \"1 min\", \"value\": 8 },\n" +
      "                    \"end_location\": { \"lat\": 34.0523523, \"lng\": -118.2435731 },\n" +
      "                    \"html_instructions\": \"Continue straight onto <b>W 1st St</b>\",\n" +
      "                    \"maneuver\": \"straight\",\n" +
      "                    \"polyline\": { \"points\": \"oyynEflupUKPIN\" },\n" +
      "                    \"start_location\":\n" +
      "                      { \"lat\": 34.0522387, \"lng\": -118.2433984 },\n" +
      "                    \"travel_mode\": \"DRIVING\",\n" +
      "                  },\n" +
      "                ],\n" +
      "              \"traffic_speed_entry\": [],\n" +
      "              \"via_waypoint\": [],\n" +
      "            },\n" +
      "          ],\n" +
      "        \"overview_polyline\":\n" +
      "          {\n" +
      "            \"points\": \"eir~FdezuOj~FnbHteIp{\\\\lmYvg{@tmUthGxwSjxAroTgT`_GfpH`uR|qO`dO|oWj_Tn_QvnPrcX|wh@pjPbsWfp\\\\pwBp}TfoRjTp`Wz`YhlWhw[lbClkTrbO~eAjjIzwGz|LxiNz~MroGloVps@~jChgFvpI`Ilzn@pBpci@fCbm[hpTxtTmXteS|aSdhTvbIf}Gv{Dh}AvoJ|rE~|P~|H`jW~{@|qRvzEhcOh}J|`c@~`Cr~a@r`DvuTbmIhiJteO|wRlsCbaLxwI~rGt_SnwXpc_@`lxApgElbWljEryV`|DjzFzmJtaYznGndb@jtBtqe@hdNp_UvfV|qZbwb@~`WbkSjb[lbDdnYxAh|]lnG`jI~nEn~s@vcPlud@zWr_qAqNjoe@`tAdpFjvF|w@xiGpuSdjIzmN|tHvnXrjS`{H~rLbwMrwJj{Zf|KfgQbeX~i]~pTfcVtqUp~e@z~KfxJtoBrtUl{IrzThmC~dSdjJrqIn{F~kRjfYlii@d|]bhyB`qM|rcAtoJnwGbrNxjCzNzti@_yFtb^kmA~yXsdB|paAn`@loeAjaCtuo@pwJlhl@daCprXz_FreUznAxsUjlQljPhnHp}`@lSp`p@_@`fp@@j{{@bdH`dk@yjDr~`Acu@t`sAvhChqm@fLtwo@caLh}nAa[t|m@j{EhzUt`Mxex@xqGbg`@w{FdeWuqB||j@~{Dde\\\\`xCheI|cAd_]`jIf_{@hbKnzh@ldHj~UifChdOwkCp`yAi~AjpdAsf@zs}AylQznn@hoCboT~~@d_QoyEfeZffL`uv@pwH`tt@mhHdk[bVzuVslFluKej@pdTvw@lcQ{hKjaT_aUbcV_mRx{HoqGx{ZmdQdfv@chIffu@j_EbeWzlMdi[rmO|hOpaKvtXpxSjwt@l{P`bn@h|Mxe_@fjF~ti@zpKhwQmsHfgWolBrg`@ksNl`y@caT|kdBqpGdbc@c|@toSnrDziUszK`cd@io@bde@l_@lcUl`Eh`LhNnug@g{J~qz@_fG~j[z{DtwSjrMv`i@ljDxma@t_E|wYuwF`fdAwiA|mYbgKrnFx`\\\\fmGfy`@zo@nqPj_TtmBlnh@gAd}KemNpdJaoJbiGiqDbtP~nBt_W`vCnyXhqFvpy@dcL~zs@j`@jm{@b[llk@wlAl_l@sxHrs]iwJhllAavEveVxJbxMvtNzjGlxYlrSp_PhvJxbTnwMd}Vx}Wh}LuqCzkLahFrtSngWduExdAcoAnmNrYjfVxc@p{UeAbiv@hqBlxQ\",\n" +
      "          },\n" +
      "        \"summary\": \"I-55 S and I-44\",\n" +
      "        \"warnings\": [],\n" +
      "        \"waypoint_order\": [0, 1],\n" +
      "      },\n" +
      "    ],\n" +
      "  \"status\": \"OK\",\n" +
      "}";
}
