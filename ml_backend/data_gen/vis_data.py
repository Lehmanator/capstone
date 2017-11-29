#!/usr/bin/env python
# -*- coding: utf-8 -*-

# Load UCI census and convert to json for sending to the visualization
import pandas as pd
# Display the Dive visualization for this data
from IPython.core.display import display, HTML


features = ["Status", "Seniority", "Home", "Time", "Age", "Marital", "Job",
            "Expenses", "Income", "Assets", "Debt", "Amount", "Price"]

jsonstr = pd.read_csv(
    "CreditScoring/CreditScoring.csv",
    names=features,
    sep=r'\s*,\s*',
    engine='python',
    skiprows=[0],
    na_values="?").to_json(orient='records')


HTML_TEMPLATE = """<link rel="import" href="/nbextensions/facets-dist/facets-jupyter.html">
        <facets-dive id="elem" height="600"></facets-dive>
        <script>
          var data = {jsonstr};
          document.querySelector("#elem").data = data;
        </script>"""
html = HTML_TEMPLATE.format(jsonstr=jsonstr)
display(HTML(html))
