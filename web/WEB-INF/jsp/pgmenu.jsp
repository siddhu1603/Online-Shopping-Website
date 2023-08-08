<div style="display: flex;flex-direction: row;">
    <div style="flex-grow: 1;">| <a href="computerLaptop">Computers/Laptops</a> | <a href="mobilePhones">Smart Phones</a> | <a href="topOffers">Top Offers</a> |</div>
    <div>
        <form name="frmsearch" id="frmsearch" action="searchShop" method="post">
            <div class="input-group mb-3">
                <table>
                    <tr>
                        <td><input type="text" style="min-width: 250px;border:1px solid lightsteelblue;" name="tsearch" id="tsearch" class="form-control" placeholder="Search Text" aria-label="Search Text" aria-describedby="basic-addon2"></td>
                        <td>
                            <div class="input-group-append">
                                <button class="btn btn-primary" type="button" onclick="searchSite(tsearch.value);">Search</button>
                            </div>
                        </td>
                    </tr>
                </table>
            </div>
        </form>
    </div>
</div>