package netology;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private ElementsCollection cards = $$(".list__item div");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";
    private SelenideElement reloadButton = $("[data-test-id=action-reload]");

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public int getCardBalance(String maskedCardNumber) {
        var text = cards.findBy(text(maskedCardNumber)).getText();
        return extractBalance(text);
    }

    public TransferPage selectCardToTransfer(String maskedCardNumber) {
        cards.findBy(text(maskedCardNumber)).$("button").click();
        return new TransferPage();
    }

    public void reloadDPage() {
        reloadButton.click();
        heading.shouldBe(visible);
    }

    private int extractBalance(String text) {
        var start = text.indexOf(balanceStart);
        var finish = text.indexOf(balanceFinish);
        var value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }
}